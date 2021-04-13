plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.AndroidX.Core)
    implementation(Dependencies.AndroidX.AppCompat)
    implementation(Dependencies.AndroidX.ConstraintLayout)
    implementation(Dependencies.Material)
    implementation(Dependencies.Glide)
    implementation(Dependencies.SwipeRefreshLayout)
    implementation(Dependencies.Jsoup)
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
    buildFeatures {
        viewBinding = true
    }
}
