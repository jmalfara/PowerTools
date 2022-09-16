plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.system"
}

dependencies {
    implementation(project(":app"))
}