plugins {
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    api(project(":common"))

    // Javax Inject
    api("javax.inject:javax.inject:1")

    // Kotlin
    api(Dependencies.Kotlin.coroutines)
}