plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.dynamic-feature")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val HILT_VERSION: String by project
val GRID_LAYOUT_VERSION: String by project
val JUNIT_VERSION: String by project
val TRUTH_VERSION: String by project
val MOCKK_VERSION: String by project
val MOSHI_VERSION: String by project
val TURBINE_VERSION: String by project
val NAVIGATION_VERSION: String by project
val FRAGMENT_TESTING_VERSION: String by project
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf("-Xjvm-default=enable")
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
    ksp ("com.squareup.moshi:moshi-kotlin-codegen:$MOSHI_VERSION")

    testImplementation ("junit:junit:${JUNIT_VERSION}")
    testImplementation ("com.google.truth:truth:${TRUTH_VERSION}")
    testImplementation ("io.mockk:mockk:${MOCKK_VERSION}")
    testImplementation ("app.cash.turbine:turbine:${TURBINE_VERSION}")

    debugImplementation ("androidx.fragment:fragment-testing:${FRAGMENT_TESTING_VERSION}")
    androidTestImplementation ("com.google.truth:truth:${TRUTH_VERSION}")
    androidTestImplementation ("io.mockk:mockk-android:${MOCKK_VERSION}")
    androidTestImplementation ("androidx.navigation:navigation-testing:${NAVIGATION_VERSION}")
    androidTestImplementation ("androidx.test.ext:junit:${ANDROID_JUNIT_VERSION}")
    androidTestImplementation ("androidx.test.espresso:espresso-core:${ESPRESSO_VERSION}")
}