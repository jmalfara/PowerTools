package com.jmat.powertools

import com.android.build.gradle.internal.plugins.BasePlugin
import org.gradle.api.Project

internal fun Project.configureDependencies() = dependencies.apply{
//    add("implementation","androidx.compose:compose-bom:2022.10.00")

    add("implementation","androidx.constraintlayout:constraintlayout:2.1.4")
    add("implementation","androidx.appcompat:appcompat:1.5.1")
    add("implementation", "com.google.android.material:material:1.7.0")
    add("implementation", "com.google.dagger:hilt-android:2.51.1")
    add("implementation", "com.google.android.material:compose-theme-adapter-3:1.0.21")
    add("implementation", "com.google.android.material:compose-theme-adapter:1.1.21")
    add("implementation", "androidx.compose.ui:ui:1.3.0")
    add("implementation", "androidx.compose.ui:ui-tooling:1.3.0")
    add("implementation", "androidx.constraintlayout:constraintlayout-compose:1.0.1")
    add("kapt", "com.google.dagger:hilt-compiler:2.51.1")

    add("testImplementation", "junit:junit:4.13.2")
    add("testImplementation", "com.google.truth:truth:1.1.3")
    add("testImplementation", "io.mockk:mockk:1.13.2")
    add("testImplementation", "app.cash.turbine:turbine:0.12.0")
    add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

//    if (project.containsAndroidPlugin()) {
        add("debugImplementation", "androidx.fragment:fragment-testing:1.5.4")
        add("androidTestImplementation", "androidx.test.ext:junit:1.1.3")
        add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.4.0")
        add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:1.1.1")
        add("androidTestImplementation", "com.google.truth:truth:1.1.3")
        add("androidTestImplementation", "io.mockk:mockk-android:1.12.4")
        add("androidTestImplementation", "androidx.navigation:navigation-testing:2.4.2")
//    }
}

//internal fun Project.containsAndroidPlugin(): Boolean {
//    return project.plugins.toList().any { plugin -> plugin is BasePlugin<*, *, *, *> }
//}