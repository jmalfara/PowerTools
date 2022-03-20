// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0-alpha03")
    }
}

// Define plugins but don't apply them
plugins {
    id ("com.android.application") version "7.1.2" apply false
    id ("com.android.library") version "7.1.2" apply false
    id ("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id ("com.android.dynamic-feature") version "7.1.2" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

