plugins {
    id("java-library")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("org.awaitility:awaitility:4.2.0")
    testImplementation("org.assertj:assertj-core:3.24.2")

    testImplementation("nl.jqno.equalsverifier:equalsverifier:3.10.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

open class TestImplementation {

    private val projects = mutableListOf<Project>()
    private val dependencies = mutableListOf<String>()

    fun useTestModuleOf(project: Project) {
        projects.add(project)
    }

    fun use(dependency: String) {
        dependencies.add(dependency)
    }

    internal fun projects(): List<Project> {
        return projects
    }

    internal fun dependencies(): List<String> {
        return dependencies
    }

}

val extension = extensions.create<TestImplementation>("liteUnit")

afterEvaluate {
    dependencies {
        extension.projects().forEach {
            testImplementation(it.sourceSets.test.get().output)
        }

        extension.dependencies().forEach {
            testImplementation(it)
        }
    }
}