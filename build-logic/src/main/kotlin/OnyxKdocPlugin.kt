import java.net.URI
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier

class OnyxKdocPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.dokka")

        if (project == rootProject) {
            configureAggregation()
        } else {
            configureKotlinModule()
        }

        if (project == rootProject) {
            listOf(
                ":onyxsdk-base:support:onyxsdk-baselite",
                ":onyxsdk-ktx",
                ":onyxsdk-pen",
            ).forEach { modulePath ->
                dependencies.add("dokka", project(modulePath))
            }
        }
    }

    private fun Project.configureAggregation() {
        extensions.configure<DokkaExtension> {
            moduleName.set("Onyx Android SDK Kotlin API")
            dokkaPublications.named("html") {
                outputDirectory.set(layout.buildDirectory.dir("dokka/html"))
                failOnWarning.set(true)
                suppressObviousFunctions.set(true)
            }
        }
    }

    private fun Project.configureKotlinModule() {
        extensions.configure<DokkaExtension> {
            moduleName.set(project.name)

            dokkaPublications.named("html") {
                failOnWarning.set(true)
                suppressObviousFunctions.set(true)
            }

            dokkaSourceSets.configureEach {
                documentedVisibilities.set(setOf(VisibilityModifier.Public))
                reportUndocumented.set(true)
                skipDeprecated.set(false)
                skipEmptyPackages.set(true)
                suppressGeneratedFiles.set(true)

                val sourceDirectory = layout.projectDirectory.dir("src/main")
                sourceRoots.setFrom(
                    fileTree(sourceDirectory) {
                        include("**/*.kt")
                    },
                )

                sourceLink {
                    localDirectory.set(sourceDirectory.asFile)
                    val modulePath = rootProject.layout.projectDirectory.asFile
                        .toPath()
                        .relativize(layout.projectDirectory.asFile.toPath())
                        .toString()
                        .replace('\\', '/')
                    remoteUrl.set(
                        URI("https://github.com/hbmartin/onyx-android-sdk/blob/main/$modulePath/src/main"),
                    )
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}
