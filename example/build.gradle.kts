import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.gradle.kotlin.dsl.kotlin

plugins {
    `android-application`
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "com.example.errorbar"
        versionCode = 1
        versionName = "1.0"
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

configurations.all {
    resolutionStrategy {
        force("com.android.support:appcompat-v7:27.1.1")
    }
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    implementation(project(":errorbar"))
    implementation(support("design", VERSION_SUPPORT))
    implementation(support("appcompat-v7", VERSION_SUPPORT))
    implementation(support("support-v4", VERSION_SUPPORT))
    implementation(androidKTX())
}