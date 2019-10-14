plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(SDK_TARGET)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "com.example.$RELEASE_ARTIFACT"
        versionName = "$VERSION_ANDROIDX-beta01"
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
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
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT-ktx"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(material("$VERSION_ANDROIDX-beta01"))
    implementation(androidx("core", "core-ktx"))
    implementation(androidx("appcompat"))
    implementation(androidx("coordinatorlayout", version = "$VERSION_ANDROIDX-beta01"))
    implementation(androidx("preference"))
}
