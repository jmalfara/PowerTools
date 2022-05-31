plugins {
    kotlin("android")
    id ("com.android.dynamic-feature")
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val HILT_VERSION: String by project

android {
    compileSdk = TARGET_SDK.toInt()

    defaultConfig {
        minSdk = MIN_SDK.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//        }
//    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation (project(":app"))
}