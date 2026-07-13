import com.android.build.api.dsl.ApplicationExtension
import java.io.File
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getByType

private data class ValidationModules(
    val common: List<OnyxModule>,
    val compared: List<OnyxModule>,
)

private class ValidationArtifacts(
    private val recoveryRoot: File,
    private val referenceRoot: File,
) {
    fun recovered(module: OnyxModule): File = recoveryRoot.resolve(module.aarRelativePath)

    fun reference(relativePath: String): File = referenceRoot.resolve(relativePath)
}

class OnyxDeviceValidationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val android = extensions.getByType<ApplicationExtension>()
        configureAndroidConvention(android)

        val recoveryRoot = rootProject.layout.projectDirectory.dir("..").asFile.canonicalFile
        val registry = OnyxModuleRegistry.load(recoveryRoot)
        val modules = registry.validationModules()
        val artifacts = ValidationArtifacts(recoveryRoot, resolveReferenceRoot())

        configureValidationDependencies(android, modules, artifacts)
        val verifyInputs = registerVerifyInputs(modules, artifacts)
        tasks.matching { it.name == "preBuild" }.configureEach {
            dependsOn(verifyInputs)
        }
    }

    private fun Project.resolveReferenceRoot(): File {
        val configuredRoot = providers.gradleProperty("OnyxArtifactsRoot").orNull
        if (configuredRoot.isNullOrBlank()) {
            throw GradleException("Pass -POnyxArtifactsRoot=/absolute/path/to/reference-artifacts")
        }
        val configuredFile = File(configuredRoot)
        return if (configuredFile.isAbsolute) {
            configuredFile.canonicalFile
        } else {
            rootProject.projectDir.resolve(configuredFile).canonicalFile
        }
    }

    private fun Project.configureValidationDependencies(
        android: ApplicationExtension,
        modules: ValidationModules,
        artifacts: ValidationArtifacts,
    ) {
        afterEvaluate {
            dependencies.add("implementation", files(modules.common.map(artifacts::recovered)))
            dependencies.add(
                "referenceImplementation",
                files(modules.compared.flatMap {
                    it.deviceValidation.referenceCompileJars.map(artifacts::reference)
                }),
            )
            dependencies.add("recoveredImplementation", files(modules.compared.map(artifacts::recovered)))

            val referenceJniDirs = modules.compared.mapNotNull {
                it.deviceValidation.referenceJniDir?.let(artifacts::reference)
            }
            android.sourceSets
                .getByName("reference")
                .jniLibs
                .directories
                .addAll(referenceJniDirs.map(File::getAbsolutePath))
        }
    }

    private fun Project.registerVerifyInputs(
        modules: ValidationModules,
        artifacts: ValidationArtifacts,
    ): TaskProvider<Task> = tasks.register("verifyInputs") {
        group = "verification"
        description = "Checks registry-declared reference and recovered validation inputs."
        doLast {
            val requiredFiles = buildList {
                addAll(modules.common.map(artifacts::recovered))
                addAll(modules.compared.map(artifacts::recovered))
                modules.compared.forEach { module ->
                    val references = module.deviceValidation.referenceCompileJars +
                        module.deviceValidation.apiReferenceJars
                    addAll(references.distinct().map(artifacts::reference))
                }
            }
            val requiredDirectories = modules.compared.mapNotNull { module ->
                module.deviceValidation.referenceJniDir?.let(artifacts::reference)
            }
            val missing = buildList {
                addAll(requiredFiles.filterNot(File::isFile))
                addAll(requiredDirectories.filterNot(File::isDirectory))
            }
            if (missing.isNotEmpty()) {
                throw GradleException(
                    "Missing validation inputs:\n${missing.joinToString("\n")}. " +
                        "Build assembleRecovered with the repository Gradle wrapper first.",
                )
            }
        }
    }

    private fun OnyxRegistry.validationModules(): ValidationModules = ValidationModules(
        common = publishedModules.filter { it.deviceValidation.commonRecovered },
        compared = publishedModules.filter { it.deviceValidation.referenceCompileJars.isNotEmpty() },
    )
}
