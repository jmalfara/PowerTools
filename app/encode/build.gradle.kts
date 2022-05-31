plugins {
    id ("com.android.dynamic-feature")
    kotlin("android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val HILT_VERSION: String by project
val MOSHI_VERSION: String by project

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
    implementation ("com.google.dagger:hilt-android:${HILT_VERSION}")
    kapt ("com.google.dagger:hilt-compiler:${HILT_VERSION}")
    ksp ("com.squareup.moshi:moshi-kotlin-codegen:${MOSHI_VERSION}")

//    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}