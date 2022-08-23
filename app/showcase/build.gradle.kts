plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.showcase"
}

dependencies {
    implementation(project(":app"))
}