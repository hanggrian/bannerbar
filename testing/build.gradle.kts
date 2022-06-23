plugins {
    id("com.android.library")
}

android.buildFeatures {
    buildConfig = false
}

dependencies {
    implementation(libs.material)
    implementation(testLibs.bundles.androidx)
}
