import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

/**
 * Registry-backed Maven platform convention used by lockstep BOM publications.
 */
class OnyxJavaPlatformPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("java-platform")
        pluginManager.apply("maven-publish")
        pluginManager.apply("signing")

        val registry = OnyxModuleRegistry.load(rootProject.layout.projectDirectory.asFile)
        val module = registry.modules.singleOrNull { it.projectPath == path }
            ?: throw GradleException("Project $path is missing from gradle/onyx-modules.json")
        require(module.artifactType == OnyxArtifactType.PLATFORM) {
            "Project $path must declare artifactType=platform"
        }
        group = registry.distribution.group
        version = module.version
        description = module.description
        extensions.configure<BasePluginExtension> {
            archivesName.set(module.artifactId)
        }

        val sources = tasks.register<Jar>("sourceReleaseJar") {
            archiveClassifier.set("sources")
        }
        val javadoc = tasks.register<Jar>("javaDocReleaseJar") {
            archiveClassifier.set("javadoc")
        }

        afterEvaluate {
            val publication = extensions.getByType<PublishingExtension>()
                .publications
                .create<MavenPublication>("release") {
                    from(components.getByName("javaPlatform"))
                    artifact(sources)
                    artifact(javadoc)
                    artifactId = module.artifactId
                    pom {
                        name.set(module.name)
                        description.set(module.description)
                        url.set(registry.distribution.projectUrl)
                        packaging = "pom"
                        licenses {
                            license {
                                val metadata = registry.distribution.licenses.getValue(module.license)
                                name.set(metadata.name)
                                url.set(metadata.url)
                                distribution.set(metadata.distribution)
                            }
                        }
                        developers {
                            developer {
                                id.set(registry.distribution.developer.id)
                                name.set(registry.distribution.developer.name)
                                url.set(registry.distribution.developer.url)
                            }
                        }
                        scm {
                            connection.set(registry.distribution.scmConnection)
                            developerConnection.set(registry.distribution.scmDeveloperConnection)
                            url.set(registry.distribution.projectUrl)
                        }
                    }
                }
            extensions.getByType<PublishingExtension>().repositories.maven {
                name = "centralStaging"
                url = uri(rootProject.layout.buildDirectory.dir("central-staging").get().asFile)
            }
            configureSigning(publication)
        }
    }

    private fun Project.configureSigning(publication: MavenPublication) {
        val signingKey = providers.environmentVariable("MAVEN_SIGNING_KEY").orNull
        val signingPassword = providers.environmentVariable("MAVEN_SIGNING_PASSWORD").orNull
        val centralRequested = gradle.startParameter.taskNames.any {
            it.contains("central", ignoreCase = true)
        }
        extensions.configure<SigningExtension> {
            setRequired(centralRequested)
            if (!signingKey.isNullOrBlank()) useInMemoryPgpKeys(signingKey, signingPassword)
            sign(publication)
        }
        tasks.withType(Sign::class.java).configureEach {
            onlyIf {
                if (!signingKey.isNullOrBlank()) {
                    true
                } else if (centralRequested) {
                    throw GradleException(
                        "Central publication requires MAVEN_SIGNING_KEY and MAVEN_SIGNING_PASSWORD",
                    )
                } else {
                    false
                }
            }
        }
    }
}
