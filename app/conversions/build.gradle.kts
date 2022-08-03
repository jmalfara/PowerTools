plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.conversions"
}

dependencies {
    implementation(project(":app"))
}