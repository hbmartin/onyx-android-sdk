import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidConvention(android: CommonExtension) {
    val kotlinVersion = extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
        .findVersion("kotlin")
        .get()
        .requiredVersion
    val kotlinStandardLibraries = setOf(
        "kotlin-stdlib",
        "kotlin-stdlib-common",
        "kotlin-stdlib-jdk7",
        "kotlin-stdlib-jdk8",
    )
    configurations.configureEach {
        if (name.endsWith("CompileClasspath") || name.endsWith("RuntimeClasspath")) {
            resolutionStrategy.eachDependency {
                if (requested.group == "org.jetbrains.kotlin" && requested.name in kotlinStandardLibraries) {
                    useVersion(kotlinVersion)
                    because("Kotlin compiler and standard-library metadata must remain aligned")
                }
            }
        }
    }

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    android.compileSdk = 35
    android.defaultConfig.minSdk = 24
    android.compileOptions.sourceCompatibility = JavaVersion.VERSION_21
    android.compileOptions.targetCompatibility = JavaVersion.VERSION_21
    android.testOptions.unitTests.isReturnDefaultValues = true
    android.lint.disable.add("GradleDependency")
}
