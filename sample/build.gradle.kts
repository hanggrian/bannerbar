plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    defaultConfig {
        minSdk = 21
        applicationId = "com.example.$RELEASE_ARTIFACT"
        multiDexEnabled = true
    }
    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT-ktx"))
    implementation(libs.material)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.preference)
    implementation(libs.auto.prefs.android)
    kapt(libs.auto.prefs.compiler)
    implementation(libs.process.phoenix)
    implementation(libs.bundles.color.preference)
}
