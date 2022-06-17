package com.jmat.powertools

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.getByType

open class PowerToolsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configureAndroid()
    }
}

internal fun Project.configureAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(ConfigData.targetSdkVersion)

    defaultConfig {
        applicationId = "com.jmat.powertools"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-beta03"
    }
}



