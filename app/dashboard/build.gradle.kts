plugins {
    id("com.jmat.powertools.dynamic-feature")
}

dependencies {
    implementation(project(":app"))
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.6.1")
}