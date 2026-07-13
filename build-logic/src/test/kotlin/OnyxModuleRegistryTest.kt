import groovy.json.JsonOutput
import java.io.File
import java.nio.file.Files
import org.gradle.api.GradleException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeNoException
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class OnyxModuleRegistryTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun acceptsProjectDirectoryInsideRepository() {
        val root = temporaryFolder.newFolder("repository-inside")
        root.resolve("module").mkdir()
        writeRegistry(root, "module")

        val registry = OnyxModuleRegistry.load(root)

        assertEquals(root.canonicalFile, registry.rootDir)
        assertEquals("module", registry.module("module").projectDir)
    }

    @Test
    fun rejectsAbsoluteProjectDirectory() {
        val root = temporaryFolder.newFolder("repository-absolute")
        val outside = temporaryFolder.newFolder("outside-absolute")
        writeRegistry(root, outside.absolutePath)

        assertOutsideRepositoryRejected(root)
    }

    @Test
    fun rejectsParentDirectoryTraversal() {
        val root = temporaryFolder.newFolder("repository-parent")
        temporaryFolder.newFolder("outside-parent")
        writeRegistry(root, "../outside-parent")

        assertOutsideRepositoryRejected(root)
    }

    @Test
    fun rejectsBackslashParentDirectoryTraversal() {
        val root = temporaryFolder.newFolder("repository-backslash")
        val projectDir = "safe\\..\\outside"
        root.resolve(projectDir).mkdirs()
        writeRegistry(root, projectDir)

        assertOutsideRepositoryRejected(root)
    }

    @Test
    fun rejectsSymlinkEscape() {
        val root = temporaryFolder.newFolder("repository-symlink")
        val outside = temporaryFolder.newFolder("outside-symlink")
        val link = root.resolve("escaping-link")
        try {
            Files.createSymbolicLink(link.toPath(), outside.toPath())
        } catch (error: Exception) {
            assumeNoException(error)
        }
        writeRegistry(root, link.name)

        assertOutsideRepositoryRejected(root)
    }

    private fun assertOutsideRepositoryRejected(root: File) {
        val error = assertThrows(GradleException::class.java) {
            OnyxModuleRegistry.load(root)
        }
        assertTrue(
            error.message.orEmpty(),
            "project directory must remain within the repository" in error.message.orEmpty(),
        )
    }

    private fun writeRegistry(root: File, projectDir: String) {
        val registryFile = root.resolve("gradle/onyx-modules.json")
        registryFile.parentFile.mkdirs()
        registryFile.writeText(
            JsonOutput.toJson(
                mapOf(
                    "schemaVersion" to 1,
                    "distribution" to mapOf(
                        "group" to "example",
                        "projectUrl" to "https://example.test",
                        "scmConnection" to "scm:git:https://example.test/repository.git",
                        "scmDeveloperConnection" to "scm:git:ssh://example.test/repository.git",
                        "developer" to mapOf(
                            "id" to "developer",
                            "name" to "Developer",
                            "url" to "https://example.test/developer",
                        ),
                        "defaultLicense" to "Test",
                        "licenses" to mapOf(
                            "Test" to mapOf(
                                "name" to "Test License",
                                "url" to "https://example.test/license",
                                "distribution" to "repo",
                            ),
                        ),
                    ),
                    "modules" to listOf(
                        mapOf(
                            "id" to "module",
                            "projectPath" to ":module",
                            "projectDir" to projectDir,
                            "artifactId" to "module",
                            "version" to "1.0.0",
                            "name" to "Module",
                            "description" to "Test module",
                            "published" to true,
                            "deviceValidation" to emptyMap<String, Any>(),
                            "projectDependencies" to emptyList<Any>(),
                            "ownedPackages" to listOf("example.module"),
                            "legacyOwnedTypes" to emptyList<Any>(),
                        ),
                    ),
                ),
            ),
        )
    }
}
