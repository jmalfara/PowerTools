import com.google.protobuf.gradle.*

plugins {
    kotlin("android")
    kotlin("kapt")
    id ("com.android.application")
    id ("dagger.hilt.android.plugin")
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.protobuf") version "0.8.17"
}

val TARGET_SDK: String by project
val MIN_SDK: String by project
val APP_VERSION_CODE: String by project
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
val RETROFIT_VERSION: String by project
val MOSHI_VERSION: String by project

android {
    compileSdk = TARGET_SDK.toInt()

    defaultConfig {
        applicationId = "com.jmat.powertools"
        minSdk = MIN_SDK.toInt()
        targetSdk = TARGET_SDK.toInt()
        versionCode = APP_VERSION_CODE.toInt()
        versionName = "APP_VERSION_NAME"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JDK_VERSION
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
    api ("androidx.core:core-ktx:${KTX_CORE_VERSION}")
    api ("androidx.appcompat:appcompat:${ANDROID_SUPPORT_PACKAGE_VERSION}")
    api ("com.google.android.material:material:${MATERIAL_VERSION}")
    api ("androidx.constraintlayout:constraintlayout:${CONSTRAINT_LAYOUT_VERSION}")
    api ("androidx.navigation:navigation-fragment-ktx:${NAVIGATION_VERSION}")
    api ("androidx.navigation:navigation-ui-ktx:${NAVIGATION_VERSION}")
    api ("androidx.lifecycle:lifecycle-runtime-ktx:${LIFECYCLE_VERSION}")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")

//    // Compose
    api ("androidx.compose.ui:ui:1.0.5")
    api ("androidx.compose.ui:ui-tooling:1.0.5")
    api ("androidx.compose.foundation:foundation:1.0.5")
    api ("androidx.compose.material:material:1.0.5")
    api ("androidx.compose.material:material-icons-core:1.0.5")
    api ("androidx.compose.material:material-icons-extended:1.0.5")
    api ("androidx.activity:activity-compose:1.3.1")
    api ("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")

    api ("com.squareup.retrofit2:retrofit:${RETROFIT_VERSION}")
    api ("com.squareup.retrofit2:converter-moshi:2.4.0")
    api ("com.squareup.moshi:moshi-kotlin:${MOSHI_VERSION}")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
//    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")

    api ("androidx.datastore:datastore:${DATASTORE_VERSION}")
    api ("androidx.datastore:datastore-core:${DATASTORE_VERSION}")
    api ("com.google.protobuf:protobuf-javalite:${PROTOBUF_VERSION}")

    implementation ("com.google.dagger:hilt-android:${HILT_VERSION}")
    kapt ("com.google.dagger:hilt-compiler:${HILT_VERSION}")

    implementation ("androidx.core:core-splashscreen:${SPLASHSCREEN_VERSION}")

    debugImplementation ("androidx.fragment:fragment-testing:1.4.1")
    testImplementation ("junit:junit:${JUNIT_VERSION}")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.14.0"
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