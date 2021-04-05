import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.pluginSerialization
    id("com.android.library")
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Coroutines.Common)
                implementation(Dependencies.Ktor.Core)
                implementation(Dependencies.Ktor.Serialization)
                implementation(Dependencies.Serialization)
                implementation(Dependencies.Reaktive.Core)
                implementation(Dependencies.Reaktive.Interop)
                implementation(Dependencies.Kodein)
                implementation(Dependencies.Settings)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Coroutines.Android)
                implementation(Dependencies.Ktor.Android)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.Ios)
            }
        }
    }
}

android {
    compileSdkVersion(Versions.targetSdk)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)
