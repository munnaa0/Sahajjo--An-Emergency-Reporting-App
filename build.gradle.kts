// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.12.0" apply false
    id("com.android.library") version "8.12.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    // Do not add google-services plugin here
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Use classpath for google services here
        classpath("com.google.gms:google-services:4.4.2")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
