plugins {
    kotlin("android")
    id("com.android.dynamic-feature")
}

android {
    compileSdk = com.jmat.powertools.ConfigData.targetSdkVersion

    defaultConfig {
        minSdk = com.jmat.powertools.ConfigData.minSdkVersion
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
}