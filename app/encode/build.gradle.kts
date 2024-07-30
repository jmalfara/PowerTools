plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.encode"
}

dependencies {
    implementation(project(":app"))
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
}