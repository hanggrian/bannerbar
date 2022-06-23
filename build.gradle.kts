import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import com.vanniktech.maven.publish.AndroidSingleVariantLibrary

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath(plugs.android)
        classpath(plugs.kotlin)
        classpath(plugs.kotlin.kover)
        classpath(plugs.dokka)
        classpath(plugs.spotless)
        classpath(plugs.maven.publish)
        classpath(plugs.pages) { features("pages-minimal") }
        classpath(plugs.git.publish)
    }
}

allprojects {
    group = RELEASE_GROUP
    version = RELEASE_VERSION
    repositories {
        mavenCentral()
        google()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io/")
    }
}

subprojects {
    afterEvaluate {
        val configureAndroid: BaseExtension.() -> Unit = {
            setCompileSdkVersion(sdk.versions.androidTarget.getInt())
            defaultConfig {
                minSdk = sdk.versions.androidMin.getInt()
                targetSdk = sdk.versions.androidTarget.getInt()
                version = RELEASE_VERSION
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                targetCompatibility = JavaVersion.VERSION_1_8
                sourceCompatibility = JavaVersion.VERSION_1_8
            }
            (this as ExtensionAware).extensions.find<KotlinJvmOptions>("kotlinOptions") {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
        extensions.find<LibraryExtension> { configureAndroid() }
        extensions.find<BaseAppModuleExtension> { configureAndroid() }
        extensions.find<KotlinProjectExtension>()?.jvmToolchain {
            (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(sdk.versions.jdk.get()))
        }
        extensions.find<MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.S01)
            signAllPublications()
            pom {
                name.set(project.name)
                description.set(RELEASE_DESCRIPTION)
                url.set(RELEASE_URL)
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
                    developerConnection.set("scm:git:ssh://git@github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT.git")
                    url.set(RELEASE_URL)
                }
                developers {
                    developer {
                        id.set(DEVELOPER_ID)
                        name.set(DEVELOPER_NAME)
                        url.set(DEVELOPER_URL)
                    }
                }
            }
            configure(AndroidSingleVariantLibrary())
        }
    }
}
