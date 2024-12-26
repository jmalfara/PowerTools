plugins {
    id("com.jmat.powertools.dynamic-feature")
    alias(libs.plugins.compose.compiler) apply false
}

android {
    namespace = "com.jmat.system"
}

dependencies {
    implementation(project(":app"))
}