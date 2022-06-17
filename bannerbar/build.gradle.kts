plugins {
    android("library")
    kotlin("android")
    `maven-publish`
    signing
}

android {
    compileSdk = SDK_TARGET
    defaultConfig {
        minSdk = SDK_MIN
        targetSdk = SDK_TARGET
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
        named("androidTest") {
            setRoot("tests")
            manifest.srcFile("tests/AndroidManifest.xml")
            java.srcDir("tests/src")
            resources.srcDir("tests/src")
        }
    }
    libraryVariants.all {
        generateBuildConfigProvider.orNull?.enabled = false
    }
}

dependencies {
    implementation(material())
    androidTestImplementation(project(":testing"))
}

tasks {
    val javadoc by registering(Javadoc::class) {
        isFailOnError = false
        source = android.sourceSets["main"].java.getSourceFiles()
        classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    }
}

mavenPublishAndroid(
    RELEASE_ARTIFACT,
    sources = android.sourceSets["main"].java.srcDirs
)
