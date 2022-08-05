import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.jmat.powertools.application")
    id("com.google.protobuf") version "0.8.17"
    id("org.jlleitschuh.gradle.ktlint")
}

val keystoreProperties = Properties().apply {
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.jmat.powertools"

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = android.signingConfigs.getByName("release")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    dynamicFeatures.addAll(
        listOf(
            ":app:dashboard",
            ":app:conversions",
            ":app:settings",
            ":app:settings",
            ":app:showcase",
            ":app:encode"
        )
    )
}

dependencies {
    api("androidx.core:core-ktx:1.8.0")
    api("androidx.navigation:navigation-fragment-ktx:2.4.2")
    api("androidx.navigation:navigation-ui-ktx:2.4.2")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.2")
    api("androidx.recyclerview:recyclerview-selection:1.1.0")
    api("com.google.android.play:core:1.10.3")

    // Compose
    api("androidx.compose.ui:ui:1.1.1")
    api("androidx.compose.ui:ui-tooling:1.1.1")
    api("androidx.compose.foundation:foundation:1.1.1")
    api("androidx.compose.material3:material3:1.0.0-alpha14")
    api("androidx.compose.material:material-icons-core:1.1.1")
    api("androidx.compose.material:material-icons-extended:1.1.1")
    api("androidx.compose.runtime:runtime-livedata:1.1.1")
    api("androidx.compose.runtime:runtime-rxjava2:1.1.1")
    api("androidx.activity:activity-compose:1.5.0")
    api("androidx.compose.animation:animation:1.1.1")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0")
    api("com.google.android.material:compose-theme-adapter-3:1.0.14")
    api("com.google.android.material:compose-theme-adapter:1.1.14")

    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-moshi:2.9.0")
    api("com.squareup.moshi:moshi-kotlin:1.13.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")

    api("androidx.datastore:datastore:1.0.0")
    api("androidx.datastore:datastore-core:1.0.0")
    api("com.google.protobuf:protobuf-javalite:3.17.3")

    api("com.github.bumptech.glide:glide:4.13.2")
    kapt("com.github.bumptech.glide:compiler:4.13.2")

    implementation("androidx.core:core-splashscreen:1.0.0-rc01")
    testApi(fileTree("${project.rootDir}/buildSrc/build/"))
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.17.3"
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach {
            it.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}
