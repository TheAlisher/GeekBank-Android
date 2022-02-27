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
    kapt(Dependencies.Hilt.compiler)

    // viewBinding
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.3")

    // Activity
    implementation("androidx.activity:activity-ktx:1.4.0")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.1")
}