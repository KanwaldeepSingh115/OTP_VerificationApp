// Root-level build.gradle.kts

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2") // Latest AGP
        classpath("com.google.gms:google-services:4.4.2") // Google Services Plugin
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") apply false
    id("kotlin-android") apply false
}

// ✅ Ensure the build directory is properly set
val newBuildDir = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.set(newBuildDir)

subprojects {
    afterEvaluate {
        val newSubprojectBuildDir = newBuildDir.dir(project.name)
        project.layout.buildDirectory.set(newSubprojectBuildDir)
    }
}

// ✅ Register clean task correctly
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
