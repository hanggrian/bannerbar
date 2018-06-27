import org.gradle.kotlin.dsl.kotlin
import org.gradle.language.base.plugins.LifecycleBasePlugin.*
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `android-library`
    `bintray-release`
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionName = VERSION_SUPPORT
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            res.srcDir("res")
        }
    }
    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

dependencies {
    api(project(":$RELEASE_ARTIFACT"))
    implementation(support("design", VERSION_SUPPORT))
}

publish {
    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = "$RELEASE_ARTIFACT-commons"
    publishVersion = VERSION_SUPPORT
    desc = RELEASE_DESC
    website = RELEASE_WEBSITE
}
