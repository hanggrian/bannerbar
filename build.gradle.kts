buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(android())
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka("android"))
        classpath(gitPublish())
        classpath(bintrayRelease())
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    register<Delete>("clean") {
        delete(buildDir)
    }
}
