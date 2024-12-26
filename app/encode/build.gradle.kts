plugins {
    id("com.jmat.powertools.dynamic-feature")
    alias(libs.plugins.compose.compiler) apply false
}

android {
    namespace = "com.jmat.encode"
}

dependencies {
    implementation(project(":app"))
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
}