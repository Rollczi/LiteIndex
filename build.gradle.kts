plugins {
    `liteindex-java`
    `liteindex-java-unit-test`
    `liteindex-repositories`
    `liteindex-publish`
}

dependencies {
    api("org.jetbrains:annotations:23.0.0")
}

litePublish {
    artifactId = "liteindex"
}