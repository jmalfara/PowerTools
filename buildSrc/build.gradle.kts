import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    /* Example Dependency */
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("com.android.tools.build:gradle:7.2.1")

    /* Example Dependency */
    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())
}