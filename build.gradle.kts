// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        repositories {
            google()
            mavenCentral()
        }
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath("com.android.tools.build:gradle:8.5.1")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
    }
}

// Define plugins but don't apply them
plugins {
    id("com.google.devtools.ksp") version "2.0.0-1.0.23" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
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
