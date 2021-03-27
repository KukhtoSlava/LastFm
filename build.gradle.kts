buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        google()
    }
    dependencies {
        classpath(Dependencies.Gradle.KotlinGradlePlugin)
        classpath(Dependencies.Gradle.Android)
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/kodein-framework/kodein-dev")
        maven("https://dl.bintray.com/kotlin/kotlinx")
        maven("https://dl.bintray.com/kotlin/ktor")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://dl.bintray.com/badoo/maven")

        google()
        jcenter()
    }
}
