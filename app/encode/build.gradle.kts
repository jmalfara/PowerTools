plugins {
    id("com.jmat.powertools.dynamic-feature")
}

dependencies {
    implementation(project(":app"))
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
}