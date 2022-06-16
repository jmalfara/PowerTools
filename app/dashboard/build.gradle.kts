plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.dynamic-feature")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = com.jmat.powertools.ConfigData.targetSdkVersion

    defaultConfig {
        minSdk = com.jmat.powertools.ConfigData.minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation (project(":app"))
    implementation ("androidx.gridlayout:gridlayout:1.0.0")

    implementation ("com.google.dagger:hilt-android:2.42")
    kapt ("com.google.dagger:hilt-compiler:2.42")
    ksp ("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("com.google.truth:truth:1.1.3")
    testImplementation ("io.mockk:mockk:1.12.4")
    testImplementation ("app.cash.turbine:turbine:0.8.0")

    debugImplementation ("androidx.fragment:fragment-testing:1.4.1")
    androidTestImplementation ("com.google.truth:truth:1.1.3")
    androidTestImplementation ("io.mockk:mockk-android:1.12.4")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.4.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}