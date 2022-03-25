plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")

    //Parcelable
    id("kotlin-parcelize")

    // Navigation Safe Args
    id(Dependencies.Navigation.safeArgsPlugin)

    // Hilt
    id(Dependencies.Hilt.plugin)
    id("com.google.gms.google-services")
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = "com.alish.geekbank"
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = 1
        versionName = "1.0"

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

    // View Binding
    buildFeatures.viewBinding = true
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    // UI Components
    implementation(Dependencies.UIComponents.material)
    implementation(Dependencies.UIComponents.constraintLayout)
    implementation(Dependencies.UIComponents.viewBindingPropertyDelegate)

    // Core
    implementation(Dependencies.Core.core)

    // Activity
    implementation(Dependencies.Activity.activity)

    // Fragment
    implementation(Dependencies.Fragment.fragment)

    // Lifecycle
    implementation(Dependencies.Lifecycle.runtime)
    implementation(Dependencies.Lifecycle.viewModel)

    // Navigation
    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)

    // Hilt
    implementation(Dependencies.Hilt.android)

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    implementation("com.google.firebase:firebase-firestore-ktx:24.0.2")
    kapt(Dependencies.Hilt.compiler)

    implementation("androidx.viewpager2:viewpager2:1.1.0-beta01")

    //Biometric
    implementation("androidx.biometric:biometric:1.1.0")

    // Material Design
    implementation("com.google.android.material:material:1.6.0-alpha03")


    // Activity
    implementation("androidx.activity:activity-ktx:1.4.0")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.1")

    // Coil
    implementation("io.coil-kt:coil:1.3.2")

    //Map
    implementation("com.google.android.gms:play-services-maps:18.0.2")

    // Qr Code Generator
    implementation("com.google.zxing:core:3.4.0")
    implementation("com.journeyapps:zxing-android-embedded:4.1.0")

    // Qr Code Scanner
    implementation("com.github.yuriy-budiyev:code-scanner:2.1.0")

    // LottieFile
    implementation(Dependencies.LottieFile.lottie)
}