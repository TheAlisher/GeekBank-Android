plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {

    api(project(":common"))

    // Javax Inject
    api("javax.inject:javax.inject:1")

    // Kotlin
    // | Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
}