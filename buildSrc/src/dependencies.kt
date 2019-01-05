import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

fun DependencyHandler.android() = "com.android.tools.build:gradle:$VERSION_ANDROID_PLUGIN"
fun PluginDependenciesSpec.android(submodule: String) = id("com.android.$submodule")

fun androidx(
    repository: String,
    module: String = repository,
    version: String = VERSION_ANDROIDX
): String = "androidx.$repository:$module:$version"

fun material() = "com.google.android.material:material:$VERSION_ANDROIDX"

fun DependencyHandler.junit() = "junit:junit:$VERSION_JUNIT"

fun DependencyHandler.ktlint() = "com.github.shyiko:ktlint:$VERSION_KTLINT"

fun DependencyHandler.dokka() = "org.jetbrains.dokka:dokka-android-gradle-plugin:$VERSION_DOKKA"
inline val PluginDependenciesSpec.dokka get() = id("org.jetbrains.dokka-android")

fun DependencyHandler.gitPublish() = "org.ajoberstar:gradle-git-publish:$VERSION_GIT_PUBLISH"
inline val PluginDependenciesSpec.`git-publish` get() = id("org.ajoberstar.git-publish")

fun DependencyHandler.bintray() = "com.jfrog.bintray.gradle:gradle-bintray-plugin:$VERSION_BINTRAY"
inline val PluginDependenciesSpec.bintray get() = id("com.jfrog.bintray")

fun DependencyHandler.bintrayRelease() = "com.novoda:bintray-release:$VERSION_BINTRAY_RELEASE"
inline val PluginDependenciesSpec.`bintray-release` get() = id("com.novoda.bintray-release")