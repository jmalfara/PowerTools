plugins {
    id("com.jmat.powertools.dynamic-feature")
    kotlin("android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation (project(":app"))
    implementation ("androidx.core:core-ktx:1.8.0")

    implementation ("com.google.dagger:hilt-android:2.42")
    kapt ("com.google.dagger:hilt-compiler:2.42")

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