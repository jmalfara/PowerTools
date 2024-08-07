plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.dashboard"
}

dependencies {
    implementation(project(":app"))
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("com.github.skydoves:landscapist-glide:2.0.3")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.google.android.material:material:1.12.0")
}