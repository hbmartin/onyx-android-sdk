import org.gradle.api.GradleException
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.register

abstract class VerifyModuleDependenciesTask : DefaultTask() {
    @get:Input
    abstract val expectedDependencies: ListProperty<String>

    @get:Input
    abstract val actualDependencies: ListProperty<String>

    @TaskAction
    fun verify() {
        val expected = expectedDependencies.get().toSet()
        val actual = actualDependencies.get().toSet()
        if (actual != expected) {
            val missing = (expected - actual).sorted()
            val unexpected = (actual - expected).sorted()
            throw GradleException(
                "Production project dependencies differ from the module registry. " +
                    "Missing=$missing, unexpected=$unexpected",
            )
        }
        logger.lifecycle("Verified production project dependency ownership")
    }
}

abstract class ValidateEnvironmentTask : DefaultTask() {
    @get:Input
    abstract val variableNames: ListProperty<String>

    @TaskAction
    fun verify() {
        val missing = variableNames.get().filter { System.getenv(it).isNullOrBlank() }
        if (missing.isNotEmpty()) {
            throw GradleException(
                "Required environment variables are missing: ${missing.joinToString()}",
            )
        }
    }
}

class OnyxRootPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        require(this == rootProject) { "onyx.root may only be applied to the root project" }
        val registry = OnyxModuleRegistry.load(layout.projectDirectory.asFile)
        group = registry.distribution.group

        tasks.register<Exec>("validateModuleRegistry") {
            group = "verification"
            description = "Validates the single module/distribution registry."
            commandLine(
                "python3",
                layout.projectDirectory.file("scripts/module_registry.py").asFile,
                "--root",
                layout.projectDirectory.asFile,
                "validate",
            )
        }

        val releaseTasks = registry.publishedModules.map(OnyxModule::releaseTask)
        tasks.register("assembleRecovered") {
            group = "build"
            description = "Builds all six source-native release AARs."
            dependsOn(releaseTasks)
        }

        tasks.register<Exec>("verifyJvmApiContracts") {
            group = "verification"
            description = "Checks the public/protected JVM contracts of every production AAR."
            dependsOn(releaseTasks)
            commandLine(
                "python3",
                layout.projectDirectory.file("scripts/jvm_api_contracts.py").asFile,
                "--root",
                layout.projectDirectory.asFile,
            )
        }

        tasks.register<Exec>("updateJvmApiContracts") {
            group = "build setup"
            description = "Explicitly replaces the checked-in JVM API contracts."
            dependsOn(releaseTasks)
            commandLine(
                "python3",
                layout.projectDirectory.file("scripts/jvm_api_contracts.py").asFile,
                "--root",
                layout.projectDirectory.asFile,
                "--update",
            )
        }

        val expectedDependencies = registry.modules.flatMap { module ->
            module.projectDependencies.map { dependency ->
                "${module.id}|${dependency.configuration}|${registry.module(dependency.target).projectPath}"
            }
        }.sorted()
        val verifyDependencies = tasks.register<VerifyModuleDependenciesTask>("verifyModuleDependencies") {
            group = "verification"
            description = "Checks production project dependencies against the module registry."
            this.expectedDependencies.set(expectedDependencies)
        }
        gradle.projectsEvaluated {
            verifyDependencies.configure {
                val actualDependencies = buildList {
                    registry.modules.forEach { module ->
                        val moduleProject = project(module.projectPath)
                        moduleProject.configurations
                            .filter { it.name in OnyxModuleRegistry.productionConfigurations }
                            .forEach { configuration ->
                                configuration.dependencies
                                    .withType(ProjectDependency::class.java)
                                    .filter { it.path != module.projectPath }
                                    .forEach { dependency ->
                                        add("${module.id}|${configuration.name}|${dependency.path}")
                                    }
                            }
                    }
                }
                this.actualDependencies.set(actualDependencies.sorted())
            }
        }

        tasks.register<Exec>("pythonValidationTest") {
            group = "verification"
            description = "Runs registry, boundary, and Central uploader unit tests."
            commandLine(
                "python3",
                "-m",
                "unittest",
                "discover",
                layout.projectDirectory.dir("device-validation/tests").asFile,
            )
        }

        tasks.register<Exec>("verifyModuleBoundaries") {
            group = "verification"
            description = "Checks source packages and classes.jar ownership for all production AARs."
            dependsOn(releaseTasks, verifyDependencies)
            commandLine(
                "python3",
                layout.projectDirectory.file("scripts/verify_module_boundaries.py").asFile,
                "--root",
                layout.projectDirectory.asFile,
            )
        }

        val publicationArtifactTasks = registry.publishedModules.flatMap { module ->
            listOf(
                "${module.projectPath}:generatePomFileForReleasePublication",
                "${module.projectPath}:generateMetadataFileForReleasePublication",
                "${module.projectPath}:sourceReleaseJar",
                "${module.projectPath}:javaDocReleaseJar",
            )
        }
        tasks.register<Exec>("verifyPublicationMetadata") {
            group = "verification"
            description = "Validates all six generated Maven publications without credentials."
            dependsOn(releaseTasks, publicationArtifactTasks)
            commandLine(
                "python3",
                layout.projectDirectory.file("scripts/verify_publications.py").asFile,
                "--root",
                layout.projectDirectory.asFile,
            )
        }

        tasks.register<Sync>("stageGithubRelease") {
            group = "distribution"
            description = "Stages versioned standalone AARs and license metadata for GitHub Releases."
            dependsOn(releaseTasks)
            into(layout.buildDirectory.dir("distributions/github"))
            registry.publishedModules.forEach { module ->
                from(layout.projectDirectory.file(module.aarRelativePath)) {
                    rename { "${module.artifactId}-${module.version}.aar" }
                }
            }
            from(layout.projectDirectory.file("LICENSE.txt"))
            from(layout.projectDirectory.file("LICENSES/GPL-3.0.txt"))
            from(layout.projectDirectory.file("LICENSES/Apache-2.0.txt"))
            from(layout.projectDirectory.file("gradle/onyx-modules.json"))
        }

        val cleanCentral = tasks.register<Delete>("cleanCentralStaging") {
            delete(layout.buildDirectory.dir("central-staging"))
        }
        val validateCentralSigning = tasks.register<ValidateEnvironmentTask>("validateCentralSigning") {
            group = "distribution"
            variableNames.set(listOf("MAVEN_SIGNING_KEY", "MAVEN_SIGNING_PASSWORD"))
        }
        val publishTasks = registry.publishedModules.map {
            "${it.projectPath}:publishReleasePublicationToCentralStagingRepository"
        }
        val stageCentral = tasks.register("stageCentralRepository") {
            group = "distribution"
            description = "Stages all signed Maven publications in a Central-compatible repository."
            dependsOn(publishTasks)
        }
        gradle.projectsEvaluated {
            registry.publishedModules.forEach { module ->
                project(module.projectPath)
                    .tasks
                    .named("publishReleasePublicationToCentralStagingRepository")
                    .configure {
                        dependsOn(cleanCentral, validateCentralSigning)
                        mustRunAfter(cleanCentral)
                    }
            }
        }
        val centralBundle = tasks.register<Zip>("centralBundle") {
            group = "distribution"
            description = "Creates the signed Maven Central Portal deployment bundle."
            dependsOn(stageCentral)
            archiveFileName.set("onyx-android-sdk-central-bundle.zip")
            destinationDirectory.set(layout.buildDirectory.dir("distributions/central"))
            from(layout.buildDirectory.dir("central-staging")) {
                val groupPath = registry.distribution.group.replace('.', '/')
                registry.publishedModules.forEach { module ->
                    include("$groupPath/${module.artifactId}/${module.version}/**")
                }
                // Gradle creates these, but Central explicitly does not require
                // checksums for detached signature files.
                exclude(
                    "**/*.asc.md5",
                    "**/*.asc.sha1",
                    "**/*.asc.sha256",
                    "**/*.asc.sha512",
                )
            }
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
        }
        tasks.register<Exec>("verifyCentralBundle") {
            group = "verification"
            description = "Validates the signed Central bundle without contacting Central."
            dependsOn(centralBundle)
            commandLine(
                "python3",
                layout.projectDirectory.file("scripts/publish_central.py").asFile,
                "--root",
                layout.projectDirectory.asFile,
                "--bundle",
                centralBundle.flatMap { it.archiveFile }.get().asFile,
                "--verify-only",
            )
        }
        Unit
    }
}
