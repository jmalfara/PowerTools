plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.dynamic-feature")
    id ("dagger.hilt.android.plugin")
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val HILT_VERSION: String by project
val GRID_LAYOUT_VERSION: String by project
val JUNIT_VERSION: String by project
val TRUTH_VERSION: String by project
val MOCKK_VERSION: String by project
val TURBINE_VERSION: String by project
val NAVIGATION_VERSION: String by project

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
    implementation ("androidx.gridlayout:gridlayout:${GRID_LAYOUT_VERSION}")

    implementation ("com.google.dagger:hilt-android:${HILT_VERSION}")
    kapt ("com.google.dagger:hilt-compiler:${HILT_VERSION}")

    testImplementation ("junit:junit:${JUNIT_VERSION}")
    testImplementation ("com.google.truth:truth:${TRUTH_VERSION}")
    testImplementation ("io.mockk:mockk:${MOCKK_VERSION}")
    testImplementation ("app.cash.turbine:turbine:${TURBINE_VERSION}")

    debugImplementation ("androidx.fragment:fragment-testing:1.4.0")
    androidTestImplementation ("com.google.truth:truth:${TRUTH_VERSION}")
    androidTestImplementation ("io.mockk:mockk-android:${MOCKK_VERSION}")
    androidTestImplementation ("androidx.navigation:navigation-testing:${NAVIGATION_VERSION}")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}