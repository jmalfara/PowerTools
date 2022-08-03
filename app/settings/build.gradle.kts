plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.settings"
}

dependencies {
    implementation(project(":app"))
}