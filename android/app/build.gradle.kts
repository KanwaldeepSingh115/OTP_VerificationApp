import java.util.Properties


plugins {
    id("com.android.application")
    id("kotlin-android")   // ✅ Updated Kotlin version
    id("org.jetbrains.kotlin.android")   // ✅ Updated Kotlin version
    id("dev.flutter.flutter-gradle-plugin")
    id("com.google.gms.google-services")
}

// ✅ Load properties from local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

// ✅ Convert string properties to integers safely
fun getIntProperty(key: String, defaultValue: Int): Int {
    return localProperties.getProperty(key)?.toIntOrNull() ?: defaultValue
}

// ✅ Retrieve correct values from local.properties
val compileSdkVersion: Int = getIntProperty("flutter.compileSdkVersion", 34)
val minSdkVersion: Int = getIntProperty("flutter.minSdkVersion", 24)
val targetSdkVersion: Int = getIntProperty("flutter.targetSdkVersion", 34)
val flutterVersionCode: Int = getIntProperty("flutter.versionCode", 1)
val flutterVersionName: String = localProperties.getProperty("flutter.versionName") ?: "1.0"

android {
    namespace = "com.example.phoneauth"
    compileSdk = compileSdkVersion?.toInt()?:35

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        applicationId = "com.example.phoneauth"
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = flutterVersionCode
        versionName = flutterVersionName
    }

    buildTypes {
        release {
            isMinifyEnabled = true  // ✅ Must be true for resource shrinking
            isShrinkResources = true // ✅ Keep this enabled
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}

dependencies {
    add("implementation", platform("com.google.firebase:firebase-bom:33.10.0"))
    add("implementation", "com.google.firebase:firebase-analytics")
}
