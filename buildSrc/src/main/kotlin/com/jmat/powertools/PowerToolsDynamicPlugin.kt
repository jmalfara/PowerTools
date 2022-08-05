package com.jmat.powertools

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.getByType
import com.android.build.gradle.`internal`.dsl.DynamicFeatureExtension

open class PowerToolsDynamicPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configureDynamicPlugins()
        project.configureDynamic()
        project.configureDependencies()
    }
}

internal fun Project.configureDynamicPlugins() = plugins.apply {
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
        kotlinCompilerExtensionVersion = "1.3.0-rc02"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

