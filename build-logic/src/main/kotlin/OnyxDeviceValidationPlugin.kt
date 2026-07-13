import com.android.build.api.dsl.ApplicationExtension
import java.io.File
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class OnyxDeviceValidationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val recoveryRoot = rootProject.layout.projectDirectory.dir("..").asFile.canonicalFile
        val registry = OnyxModuleRegistry.load(recoveryRoot)
        val artifactsRoot = providers.gradleProperty("OnyxArtifactsRoot").orNull
            ?.let(::File)
            ?.let { if (it.isAbsolute) it else rootProject.projectDir.resolve(it) }
            ?.canonicalFile
            ?: throw GradleException(
                "Pass -POnyxArtifactsRoot=/absolute/path/to/reference-artifacts",
            )

        fun recovered(module: OnyxModule): File = recoveryRoot.resolve(module.aarRelativePath)
        fun original(relativePath: String): File = artifactsRoot.resolve(relativePath)

        val commonModules = registry.publishedModules.filter {
            it.deviceValidation.commonRecovered
        }
        val comparedModules = registry.publishedModules.filter {
            it.deviceValidation.referenceCompileJars.isNotEmpty()
        }

        afterEvaluate {
            dependencies.add("implementation", files(commonModules.map(::recovered)))
            dependencies.add(
                "referenceImplementation",
                files(comparedModules.flatMap {
                    it.deviceValidation.referenceCompileJars.map(::original)
                }),
            )
            dependencies.add("recoveredImplementation", files(comparedModules.map(::recovered)))

            val referenceJniDirs = comparedModules.mapNotNull {
                it.deviceValidation.referenceJniDir?.let(::original)
            }
            extensions.getByType<ApplicationExtension>()
                .sourceSets
                .getByName("reference")
                .jniLibs
                .directories
                .addAll(referenceJniDirs.map(File::getAbsolutePath))
        }

        val verifyInputs = tasks.register("verifyInputs") {
            group = "verification"
            description = "Checks registry-declared reference and recovered validation inputs."
            doLast {
                val requiredFiles = buildList {
                    addAll(commonModules.map(::recovered))
                    addAll(comparedModules.map(::recovered))
                    comparedModules.forEach { module ->
                        val references = module.deviceValidation.referenceCompileJars +
                            module.deviceValidation.apiReferenceJars
                        addAll(references.distinct().map(::original))
                    }
                }
                val requiredDirectories = comparedModules.mapNotNull { module ->
                    module.deviceValidation.referenceJniDir?.let(::original)
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
        tasks.matching { it.name == "preBuild" }.configureEach {
            dependsOn(verifyInputs)
        }
    }
}
