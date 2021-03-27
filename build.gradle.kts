buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.KotlinGradlePlugin)
        classpath(Dependencies.Gradle.Android)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
