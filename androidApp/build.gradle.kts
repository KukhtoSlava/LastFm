plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.AndroidX.AppCompat)
    implementation(Dependencies.AndroidX.ConstraintLayout)
}

android {
    compileSdkVersion(Versions.targetSdk)
    defaultConfig {
        applicationId = "com.slavakukhto.lastfm.androidApp"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
