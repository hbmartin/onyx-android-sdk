import groovy.json.JsonSlurper
import java.io.File
import org.gradle.api.GradleException

data class OnyxLicense(
    val id: String,
    val name: String,
    val url: String,
    val distribution: String,
)

data class OnyxDeveloper(
    val id: String,
    val name: String,
    val url: String,
)

data class OnyxDistribution(
    val group: String,
    val projectUrl: String,
    val scmConnection: String,
    val scmDeveloperConnection: String,
    val developer: OnyxDeveloper,
    val defaultLicense: String,
    val licenses: Map<String, OnyxLicense>,
)

data class OnyxDeviceValidation(
    val commonRecovered: Boolean,
    val referenceCompileJars: List<String>,
    val apiReferenceJars: List<String>,
    val referenceJniDir: String?,
)

data class OnyxProjectDependency(
    val configuration: String,
    val target: String,
)

data class OnyxModule(
    val id: String,
    val projectPath: String,
    val projectDir: String,
    val artifactId: String,
    val version: String,
    val name: String,
    val description: String,
    val published: Boolean,
    val license: String,
    val deviceValidation: OnyxDeviceValidation,
    val projectDependencies: List<OnyxProjectDependency>,
    val ownedPackages: List<String>,
    val legacyOwnedTypes: List<String>,
) {
    val releaseTask: String
        get() = "$projectPath:assembleRelease"

    val aarRelativePath: String
        get() = "$projectDir/build/outputs/aar/$artifactId-release.aar"
}

data class OnyxRegistry(
    val rootDir: File,
    val distribution: OnyxDistribution,
    val modules: List<OnyxModule>,
) {
    val publishedModules: List<OnyxModule>
        get() = modules.filter(OnyxModule::published)

    fun module(id: String): OnyxModule = modules.singleOrNull { it.id == id }
        ?: throw GradleException("Unknown Onyx module id: $id")
}

object OnyxModuleRegistry {
    private const val REGISTRY_PATH = "gradle/onyx-modules.json"
    private val productionConfigurations = setOf("api", "implementation", "compileOnly", "runtimeOnly")

    fun load(rootDir: File, validatePaths: Boolean = true): OnyxRegistry {
        val canonicalRoot = rootDir.canonicalFile
        val registryFile = canonicalRoot.resolve(REGISTRY_PATH)
        val payload = try {
            JsonSlurper().parse(registryFile) as? Map<*, *>
        } catch (error: Exception) {
            throw GradleException("Could not load module registry $registryFile", error)
        } ?: throw GradleException("Module registry must contain a JSON object: $registryFile")
        requireRegistry(payload["schemaVersion"] == 1, "schemaVersion must be 1")

        val distributionMap = payload.requiredMap("distribution", "registry")
        val developerMap = distributionMap.requiredMap("developer", "distribution")
        val licensesMap = distributionMap.requiredMap("licenses", "distribution")
        requireRegistry(licensesMap.isNotEmpty(), "distribution.licenses must not be empty")
        val licenses = licensesMap.entries.associate { (rawId, rawLicense) ->
            val id = rawId as? String
                ?: throw GradleException("distribution.licenses keys must be strings")
            val license = rawLicense.asMap("distribution.licenses.$id")
            id to OnyxLicense(
                id = id,
                name = license.requiredString("name", "distribution.licenses.$id"),
                url = license.requiredString("url", "distribution.licenses.$id"),
                distribution = license.requiredString("distribution", "distribution.licenses.$id"),
            )
        }
        val distribution = OnyxDistribution(
            group = distributionMap.requiredString("group", "distribution"),
            projectUrl = distributionMap.requiredString("projectUrl", "distribution"),
            scmConnection = distributionMap.requiredString("scmConnection", "distribution"),
            scmDeveloperConnection = distributionMap.requiredString("scmDeveloperConnection", "distribution"),
            developer = OnyxDeveloper(
                id = developerMap.requiredString("id", "distribution.developer"),
                name = developerMap.requiredString("name", "distribution.developer"),
                url = developerMap.requiredString("url", "distribution.developer"),
            ),
            defaultLicense = distributionMap.requiredString("defaultLicense", "distribution"),
            licenses = licenses,
        )
        requireRegistry(
            distribution.defaultLicense in licenses,
            "distribution.defaultLicense is unknown: ${distribution.defaultLicense}",
        )

        val rawModules = payload["modules"] as? List<*>
            ?: throw GradleException("registry.modules must be a list")
        requireRegistry(rawModules.isNotEmpty(), "registry.modules must not be empty")
        val modules = rawModules.mapIndexed { index, rawModule ->
            parseModule(rawModule.asMap("modules[$index]"), index, distribution.defaultLicense)
        }
        requireUnique("id", modules.map(OnyxModule::id))
        requireUnique("projectPath", modules.map(OnyxModule::projectPath))
        requireUnique("projectDir", modules.map(OnyxModule::projectDir))
        requireUnique("artifactId", modules.filter(OnyxModule::published).map(OnyxModule::artifactId))
        requireUnique(
            "coordinates",
            modules.filter(OnyxModule::published).map {
                "${distribution.group}:${it.artifactId}:${it.version}"
            },
        )

        val byId = modules.associateBy(OnyxModule::id)
        modules.forEach { module ->
            requireRegistry(module.license in licenses, "${module.id} uses unknown license ${module.license}")
            if (validatePaths) {
                requireRegistry(
                    canonicalRoot.resolve(module.projectDir).isDirectory,
                    "${module.id} project directory does not exist: ${module.projectDir}",
                )
            }
            module.projectDependencies.forEach { dependency ->
                val target = byId[dependency.target]
                    ?: throw GradleException("${module.id} depends on unknown module ${dependency.target}")
                requireRegistry(
                    target.published,
                    "${module.id} has a production dependency on non-published ${target.id}",
                )
            }
        }

        val packageOwners = mutableMapOf<String, String>()
        val validationOwners = mutableMapOf<String, String>()
        val legacyOwners = mutableMapOf<String, String>()
        modules.filter(OnyxModule::published).forEach { module ->
            val validationPaths = buildSet {
                addAll(module.deviceValidation.referenceCompileJars)
                addAll(module.deviceValidation.apiReferenceJars)
                module.deviceValidation.referenceJniDir?.let(::add)
            }
            validationPaths.forEach { path ->
                val previous = validationOwners.putIfAbsent(path, module.id)
                requireRegistry(
                    previous == null || previous == module.id,
                    "Validation input $path belongs to both $previous and ${module.id}",
                )
            }
            module.ownedPackages.forEach { packageName ->
                val previous = packageOwners.putIfAbsent(packageName, module.id)
                requireRegistry(
                    previous == null || previous == module.id,
                    "Package $packageName is owned by both $previous and ${module.id}",
                )
            }
        }
        modules.filter(OnyxModule::published).forEach { module ->
            module.legacyOwnedTypes.forEach { typeName ->
                val packageName = typeName.substringBeforeLast('.', missingDelimiterValue = "")
                requireRegistry(
                    packageName in packageOwners,
                    "Legacy type $typeName has no default package owner",
                )
                val previous = legacyOwners.putIfAbsent(typeName, module.id)
                requireRegistry(
                    previous == null || previous == module.id,
                    "Legacy type $typeName is owned by both $previous and ${module.id}",
                )
            }
        }
        modules.filterNot(OnyxModule::published).forEach { module ->
            requireRegistry(
                module.deviceValidation == OnyxDeviceValidation(false, emptyList(), emptyList(), null),
                "Non-published module ${module.id} cannot define device validation roles",
            )
        }
        return OnyxRegistry(canonicalRoot, distribution, modules)
    }

    private fun parseModule(module: Map<*, *>, index: Int, defaultLicense: String): OnyxModule {
        val context = "modules[$index]"
        val validation = module.optionalMap("deviceValidation", context)
        val dependencies = module.optionalList("projectDependencies", context).mapIndexed { depIndex, raw ->
            val depContext = "$context.projectDependencies[$depIndex]"
            val dependency = raw.asMap(depContext)
            val configuration = dependency.requiredString("configuration", depContext)
            requireRegistry(
                configuration in productionConfigurations,
                "$depContext.configuration is not a production configuration",
            )
            OnyxProjectDependency(
                configuration = configuration,
                target = dependency.requiredString("target", depContext),
            )
        }
        requireUnique("$context.projectDependencies", dependencies.map { "${it.configuration}:${it.target}" })
        val referenceCompileJars = validation.stringList(
            "referenceCompileJars",
            "$context.deviceValidation",
        )
        val apiReferenceJars = validation.stringList(
            "apiReferenceJars",
            "$context.deviceValidation",
        )
        referenceCompileJars.forEach {
            validateReference(it, "$context.deviceValidation.referenceCompileJars", jar = true)
        }
        apiReferenceJars.forEach {
            validateReference(it, "$context.deviceValidation.apiReferenceJars", jar = true)
        }
        val referenceJniDir = validation.optionalString(
            "referenceJniDir",
            "$context.deviceValidation",
        )
        referenceJniDir?.let {
            validateReference(it, "$context.deviceValidation.referenceJniDir", jar = false)
        }
        return OnyxModule(
            id = module.requiredString("id", context),
            projectPath = module.requiredString("projectPath", context),
            projectDir = module.requiredString("projectDir", context),
            artifactId = module.requiredString("artifactId", context),
            version = module.requiredString("version", context),
            name = module.requiredString("name", context),
            description = module.requiredString("description", context),
            published = module.requiredBoolean("published", context),
            license = module.optionalString("license", context) ?: defaultLicense,
            deviceValidation = OnyxDeviceValidation(
                commonRecovered = validation.optionalBoolean("commonRecovered", "$context.deviceValidation"),
                referenceCompileJars = referenceCompileJars,
                apiReferenceJars = apiReferenceJars,
                referenceJniDir = referenceJniDir,
            ),
            projectDependencies = dependencies,
            ownedPackages = module.stringList("ownedPackages", context),
            legacyOwnedTypes = module.stringList("legacyOwnedTypes", context),
        )
    }

    private fun requireUnique(label: String, values: List<String>) {
        val duplicates = values.groupingBy { it }.eachCount().filterValues { it > 1 }.keys.sorted()
        requireRegistry(duplicates.isEmpty(), "Duplicate module $label: ${duplicates.joinToString()}")
    }

    private fun validateReference(value: String, context: String, jar: Boolean) {
        val parts = value.split('/')
        requireRegistry(
            !value.startsWith('/') && '\\' !in value && parts.none { it.isEmpty() || it == "." || it == ".." },
            "$context must be a normalized relative path",
        )
        requireRegistry(!jar || value.endsWith(".jar"), "$context must reference a .jar file")
    }

    private fun requireRegistry(condition: Boolean, message: String) {
        if (!condition) throw GradleException("Invalid Onyx module registry: $message")
    }

    private fun Any?.asMap(context: String): Map<*, *> = this as? Map<*, *>
        ?: throw GradleException("Invalid Onyx module registry: $context must be an object")

    private fun Map<*, *>.requiredMap(key: String, context: String): Map<*, *> =
        this[key].asMap("$context.$key")

    private fun Map<*, *>.optionalMap(key: String, context: String): Map<*, *> =
        if (containsKey(key)) requiredMap(key, context) else emptyMap<Any, Any>()

    private fun Map<*, *>.requiredString(key: String, context: String): String {
        val value = this[key] as? String
        requireRegistry(!value.isNullOrBlank(), "$context.$key must be a non-empty string")
        return value!!
    }

    private fun Map<*, *>.optionalString(key: String, context: String): String? {
        if (!containsKey(key)) return null
        return requiredString(key, context)
    }

    private fun Map<*, *>.requiredBoolean(key: String, context: String): Boolean =
        this[key] as? Boolean
            ?: throw GradleException("Invalid Onyx module registry: $context.$key must be a boolean")

    private fun Map<*, *>.optionalBoolean(key: String, context: String): Boolean =
        if (containsKey(key)) requiredBoolean(key, context) else false

    private fun Map<*, *>.optionalList(key: String, context: String): List<*> =
        if (containsKey(key)) {
            this[key] as? List<*>
                ?: throw GradleException("Invalid Onyx module registry: $context.$key must be a list")
        } else {
            emptyList<Any>()
        }

    private fun Map<*, *>.stringList(key: String, context: String): List<String> {
        val values = optionalList(key, context).map { item ->
            item as? String
                ?: throw GradleException("Invalid Onyx module registry: $context.$key must contain strings")
        }
        requireUnique("$context.$key", values)
        return values
    }
}
