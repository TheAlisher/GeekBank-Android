plugins {
    id("com.android.library")
    kotlin("android")
    id(Dependencies.Kotlin.ksp) version Dependencies.Kotlin.kspVersion
    id("com.google.gms.google-services")
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain"))

    // Retrofit 2
    implementation(Dependencies.Retrofit2.retrofit)
    implementation(Dependencies.Retrofit2.converterGson)

    // OkHttp
    implementation(Dependencies.OkHttp3.bom)
    implementation(Dependencies.OkHttp3.okHttp)
    implementation(Dependencies.OkHttp3.loggingInterceptor)

    // Room
    api(Dependencies.Room.runtime)
    implementation("com.google.firebase:firebase-firestore-ktx:24.0.0")
    implementation("com.google.firebase:firebase-storage:20.0.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.1")
    ksp(Dependencies.Room.compiler)
    implementation(Dependencies.Room.supportKotlinExtensionsAndCoroutines)

    // Paging 3
    api(Dependencies.Paging3.runtime)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.9")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
}