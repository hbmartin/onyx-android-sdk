import org.gradle.api.GradleException
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertThrows
import org.junit.Test

class VerifyModuleDependenciesTaskTest {
    private fun task(
        expected: List<String>,
        actual: List<String>,
    ): VerifyModuleDependenciesTask {
        val project = ProjectBuilder.builder().build()
        return project.tasks.register(
            "verifyDependencies",
            VerifyModuleDependenciesTask::class.java,
        ).get().apply {
            expectedDependencies.set(expected)
            actualDependencies.set(actual)
        }
    }

    @Test
    fun acceptsRegistryDeclaredDependencies() {
        task(
            expected = listOf("base|api|:device"),
            actual = listOf("base|api|:device"),
        ).verify()
    }

    @Test
    fun rejectsUnauthorizedProjectDependencies() {
        val error = assertThrows(GradleException::class.java) {
            task(
                expected = listOf("base|api|:device"),
                actual = listOf("base|api|:device", "device|implementation|:base"),
            ).verify()
        }
        check("device|implementation|:base" in error.message.orEmpty())
    }
}
