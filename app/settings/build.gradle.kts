plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.dynamic-feature")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = com.jmat.powertools.ConfigData.targetSdkVersion

    defaultConfig {
        minSdk = com.jmat.powertools.ConfigData.minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation (project(":app"))

    implementation ("com.google.dagger:hilt-android:2.42")
    kapt ("com.google.dagger:hilt-compiler:2.42")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.annotation:annotation:1.3.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}