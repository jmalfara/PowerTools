plugins {
    kotlin("android")
    id("com.android.dynamic-feature")
}

android {
    compileSdk = ConfigData.targetSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }
}

dependencies {
    implementation(project(":app"))

    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    implementation("androidx.compose.foundation:foundation:1.1.1")
    implementation("androidx.compose.material3:material3:1.0.0-alpha13")
    implementation("androidx.compose.material:material-icons-core:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.1.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.1.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.compose.animation:animation:1.1.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    implementation("com.google.android.material:compose-theme-adapter-3:1.0.11")
    implementation("com.google.android.material:compose-theme-adapter:1.1.11")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
}