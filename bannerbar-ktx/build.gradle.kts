plugins {
    id("com.android.library")
    kotlin("android")
    id("com.diffplug.spotless")
    id("com.vanniktech.maven.publish.base")
}

android.buildFeatures {
    buildConfig = false
}

spotless.kotlin { ktlint() }

dependencies {
    api(project(":$RELEASE_ARTIFACT"))
    implementation(libs.material)
    androidTestImplementation(project(":testing"))
}
