import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

private const val ANDROID_COMPILE_SDK = 35
private const val ANDROID_MIN_SDK = 24
private const val JVM_TOOLCHAIN_VERSION = 21

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
        toolchain.languageVersion.set(JavaLanguageVersion.of(JVM_TOOLCHAIN_VERSION))
    }

    android.compileSdk = ANDROID_COMPILE_SDK
    android.defaultConfig.minSdk = ANDROID_MIN_SDK
    android.compileOptions.sourceCompatibility = JavaVersion.VERSION_21
    android.compileOptions.targetCompatibility = JavaVersion.VERSION_21
    android.testOptions.unitTests.isReturnDefaultValues = true
    android.lint.disable.add("GradleDependency")
}
