plugins {
    id("com.jmat.powertools.dynamic-feature")
}

android {
    namespace = "com.jmat.showcase"
}

dependencies {
    implementation("androidx.compose.ui:ui:1.2.0")
    implementation("androidx.compose.ui:ui-tooling:1.2.0")
    implementation("androidx.compose.foundation:foundation:1.2.0")
    implementation("androidx.compose.material3:material3:1.0.0-alpha15")
    implementation("androidx.compose.material:material-icons-core:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.2.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.0")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.2.0")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.compose.animation:animation:1.2.0")

    implementation(project(":app"))
}