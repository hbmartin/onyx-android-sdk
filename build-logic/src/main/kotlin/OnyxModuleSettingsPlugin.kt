import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class OnyxModuleSettingsPlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
        val registry = OnyxModuleRegistry.load(target.settingsDir)
        registry.modules.forEach { module ->
            target.include(module.projectPath)
            target.project(module.projectPath).projectDir = target.settingsDir.resolve(module.projectDir)
        }
    }
}
