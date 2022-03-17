plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.dynamic-feature")
    id ("dagger.hilt.android.plugin")
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val HILT_VERSION: String by project
val ANNOTATIONS_VERSION: String by project
val ANDROID_JUNIT_VERSION: String by project
val ESPRESSO_VERSION: String by project

android {
    compileSdk = TARGET_SDK.toInt()

    defaultConfig {
        minSdk = MIN_SDK.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation (project(":app"))

    implementation ("com.google.dagger:hilt-android:${HILT_VERSION}")
    kapt ("com.google.dagger:hilt-compiler:${HILT_VERSION}")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.annotation:annotation:${ANNOTATIONS_VERSION}")
    androidTestImplementation ("androidx.test.ext:junit:${ANDROID_JUNIT_VERSION}")
    androidTestImplementation ("androidx.test.espresso:espresso-core:${ESPRESSO_VERSION}")
}