package com.jmat.powertools

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.BaseExtension
import org.gradle.kotlin.dsl.getByType

open class PowerToolsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configureAndroid()
    }
}

internal fun Project.configureAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(ConfigData.minSdkVersion)
}



