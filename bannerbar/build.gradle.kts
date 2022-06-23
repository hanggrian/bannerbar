plugins {
    id("com.android.library")
    id("com.diffplug.spotless")
    id("com.vanniktech.maven.publish.base")
}

android.buildFeatures {
    buildConfig = false
}

spotless.java { googleJavaFormat() }

dependencies {
    implementation(libs.material)
    androidTestImplementation(project(":testing"))
}
