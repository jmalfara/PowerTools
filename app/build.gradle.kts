import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc
import java.util.Properties
import java.io.FileInputStream

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.protobuf") version "0.8.17"
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.devtools.ksp")
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val APP_VERSION_CODE: String by project
val APP_VERSION_NAME: String by project
val JDK_VERSION: String by project
val HILT_VERSION: String by project
val DATASTORE_VERSION: String by project
val PROTOBUF_VERSION: String by project
val KTX_CORE_VERSION: String by project
val ANDROID_SUPPORT_PACKAGE_VERSION: String by project
val MATERIAL_VERSION: String by project
val CONSTRAINT_LAYOUT_VERSION: String by project
val NAVIGATION_VERSION: String by project
val LIFECYCLE_VERSION: String by project
val SPLASHSCREEN_VERSION: String by project
val JUNIT_VERSION: String by project
val TRUTH_VERSION: String by project
val MOCKK_VERSION: String by project
val TURBINE_VERSION: String by project
val RETROFIT_VERSION: String by project
val MOSHI_VERSION: String by project

val keystoreProperties = Properties().apply {
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    load(FileInputStream(keystorePropertiesFile))
}

android {
    compileSdk = TARGET_SDK.toInt()

    defaultConfig {
        applicationId = "com.jmat.powertools"
        minSdk = MIN_SDK.toInt()
        targetSdk = TARGET_SDK.toInt()
        versionCode = APP_VERSION_CODE.toInt()
        versionName = APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf("-Xjvm-default=enable")
    }

    buildFeatures {
        viewBinding = true
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
    api("androidx.core:core-ktx:$KTX_CORE_VERSION")
    api("androidx.appcompat:appcompat:$ANDROID_SUPPORT_PACKAGE_VERSION")
    api("com.google.android.material:material:$MATERIAL_VERSION")
    api("androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION")
    api("androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION")
    api("androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION")
    api("androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    api("androidx.recyclerview:recyclerview-selection:1.1.0")
    api("com.google.android.play:core:1.10.3")

    api("com.squareup.retrofit2:retrofit:$RETROFIT_VERSION")
    api("com.squareup.retrofit2:converter-moshi:2.4.0")
    api("com.squareup.moshi:moshi-kotlin:$MOSHI_VERSION")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$MOSHI_VERSION")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    api("androidx.datastore:datastore:$DATASTORE_VERSION")
    api("androidx.datastore:datastore-core:$DATASTORE_VERSION")
    api("com.google.protobuf:protobuf-javalite:$PROTOBUF_VERSION")

    api("com.github.bumptech.glide:glide:4.13.0")
    kapt("com.github.bumptech.glide:compiler:4.13.0")

    implementation("com.google.dagger:hilt-android:$HILT_VERSION")
    kapt("com.google.dagger:hilt-compiler:$HILT_VERSION")

    implementation("androidx.core:core-splashscreen:$SPLASHSCREEN_VERSION")

    debugImplementation("androidx.fragment:fragment-testing:1.4.1")
    testApi("junit:junit:$JUNIT_VERSION")
    testApi("com.google.truth:truth:${TRUTH_VERSION}")
    testApi("io.mockk:mockk:${MOCKK_VERSION}")
    testApi("app.cash.turbine:turbine:${TURBINE_VERSION}")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${PROTOBUF_VERSION}"
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
