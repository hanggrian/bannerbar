plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdk = SDK_TARGET
    defaultConfig {
        minSdk = 21
        targetSdk = SDK_TARGET
        applicationId = "com.example.$RELEASE_ARTIFACT"
        versionName = RELEASE_VERSION
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    lint {
        isAbortOnError = false
    }
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT-ktx"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(material())
    implementation(androidx("core", "core-ktx"))
    implementation(androidx("appcompat"))
    implementation(androidx("coordinatorlayout", version = "1.1.0"))
    implementation(androidx("preference", version = "1.1.0"))
    implementation(hendraanggrian("auto.prefs", "prefs-android", VERSION_PREFS))
    kapt(hendraanggrian("auto.prefs", "prefs-compiler", VERSION_PREFS))
    implementation(processPhoenix())
    implementation(colorPreference("core"))
    implementation(colorPreference("support"))
}
