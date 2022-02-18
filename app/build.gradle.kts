plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")

    // Navigation Safe Args
    id(Dependencies.Navigation.safeArgsPlugin)

    // Hilt
    id(Dependencies.Hilt.plugin)
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.alish.geekbank"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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

    buildFeatures {

        // View Binding
        viewBinding = true
    }
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
    kapt(Dependencies.Hilt.compiler)
}