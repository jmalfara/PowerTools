// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        repositories {
            google()
            mavenCentral()
        }
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0-rc01")
        classpath("com.android.tools.build:gradle:7.2.1")
    }
}

// Define plugins but don't apply them
plugins {
    id("com.google.devtools.ksp") version "1.6.21-1.0.5" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        with(kotlinOptions) {
            freeCompilerArgs = listOf(
                "-Xjvm-default=all",
                "-opt-in=kotlin.RequiresOptIn"
            )
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}