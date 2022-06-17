plugins {
    android("library")
    kotlin("android")
}

android {
    compileSdk = SDK_TARGET
    defaultConfig {
        minSdk = SDK_MIN
        targetSdk = SDK_TARGET
        multiDexEnabled = true
    }
    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    libraryVariants.all {
        generateBuildConfigProvider.orNull?.enabled = false
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("test-junit", VERSION_KOTLIN))
    implementation(material())
    implementation(androidx("multidex", version = VERSION_MULTIDEX))
    implementation(androidx("core", "core-ktx"))
    implementation(androidx("appcompat"))
    implementation(androidx("coordinatorlayout", version = "1.1.0"))
    implementation(androidx("test", "core-ktx", VERSION_ANDROIDX_TEST))
    implementation(androidx("test", "runner", VERSION_ANDROIDX_TEST))
    implementation(androidx("test", "rules", VERSION_ANDROIDX_TEST))
    implementation(androidx("test.ext", "junit-ktx", VERSION_ANDROIDX_JUNIT))
    implementation(androidx("test.ext", "truth", VERSION_ANDROIDX_TRUTH))
    implementation(androidx("test.espresso", "espresso-core", VERSION_ESPRESSO))
}
