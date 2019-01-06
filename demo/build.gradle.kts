plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "$RELEASE_GROUP.$RELEASE_ARTIFACT.demo"
        versionCode = 1
        versionName = "$VERSION_ANDROIDX-alpha02"
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
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT-ktx"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(material("$VERSION_ANDROIDX-alpha02"))
    implementation(androidx("core", "core-ktx", "$VERSION_ANDROIDX-alpha03"))
    implementation(androidx("appcompat", version = "$VERSION_ANDROIDX-alpha01"))
    implementation(androidx("coordinatorlayout", version = "$VERSION_ANDROIDX-alpha01"))
}