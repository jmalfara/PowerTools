package com.jmat.powertools

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.getByType
import com.android.build.gradle.`internal`.dsl.DynamicFeatureExtension
import com.android.build.gradle.internal.plugins.BasePlugin

open class PowerToolsDynamicPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurePlugins()
        project.configureDynamic()
        project.configureDependencies()
    }
}

internal fun Project.configurePlugins() = plugins.apply {
    apply("com.android.dynamic-feature")
    apply("dagger.hilt.android.plugin")
    apply("com.google.devtools.ksp")
    apply("org.jetbrains.kotlin.android")
    apply("org.jetbrains.kotlin.kapt")
    apply("androidx.navigation.safeargs.kotlin")
}

internal fun Project.configureDynamic() = this.extensions.getByType<DynamicFeatureExtension>().run {
    compileSdk = ConfigData.targetSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

internal fun Project.configureDependencies() = dependencies.apply{
    add("implementation", "com.google.dagger:hilt-android:2.42")
    add("kapt", "com.google.dagger:hilt-compiler:2.42")

    add("testImplementation", "junit:junit:4.13.2")
    add("testImplementation", "com.google.truth:truth:1.1.3")
    add("testImplementation", "io.mockk:mockk:1.12.4")
    add("testImplementation", "app.cash.turbine:turbine:0.8.0")
    add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")

    if (project.containsAndroidPlugin()) {
        add("debugImplementation", "androidx.fragment:fragment-testing:1.4.1")
        add("androidTestImplementation", "androidx.test.ext:junit:1.1.3")
        add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.4.0")
        add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:1.1.1")
        add("androidTestImplementation", "com.google.truth:truth:1.1.3")
        add("androidTestImplementation", "io.mockk:mockk-android:1.12.4")
        add("androidTestImplementation", "androidx.navigation:navigation-testing:2.4.2")
    }
}

internal fun Project.containsAndroidPlugin(): Boolean {
    return project.plugins.toList().any { plugin -> plugin is BasePlugin<*, *, *, *> }
}
