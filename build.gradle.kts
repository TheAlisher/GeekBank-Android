import org.apache.tools.ant.util.JavaEnvUtils.VERSION_1_8

val sourceCompatibility by extra(VERSION_1_8)
val targetCompatibility by extra(VERSION_1_8)
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
    }
}
plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    kotlin("android") version "1.6.10" apply false

    // Navigation Safe Args
    id(Dependencies.Navigation.safeArgsPlugin) version Dependencies.Navigation.version apply false

    // Hilt
    id(Dependencies.Hilt.plugin) version Dependencies.Hilt.version apply false
}