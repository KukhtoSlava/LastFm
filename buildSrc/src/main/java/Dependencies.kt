private typealias dep = LibraryDependency

object Dependencies {

    object Gradle {

        object KotlinGradlePlugin : dep(
            name = "org.jetbrains.kotlin:kotlin-gradle-plugin",
            version = Versions.kotlin
        )

        object Android : dep(
            name = "com.android.tools.build:gradle",
            version = Versions.gradle
        )
    }

    object AndroidX {

        object AppCompat : dep(
            name = "androidx.appcompat:appcompat",
            version = Versions.supportLib
        )

        object ConstraintLayout : dep(
            name = "androidx.constraintlayout:constraintlayout",
            version = Versions.constraintLayout
        )
    }

    object Coroutines {

        object Common : dep(
            name = "org.jetbrains.kotlinx:kotlinx-coroutines-core",
            version = Versions.coroutines
        )

        object Android : dep(
            name = "org.jetbrains.kotlinx:kotlinx-coroutines-android",
            version = Versions.coroutines
        )
    }

    object Ktor {

        object Core : dep(
            name = "io.ktor:ktor-client-core",
            version = Versions.ktor
        )

        object Android : dep(
            name = "io.ktor:ktor-client-android",
            version = Versions.ktor
        )

        object Ios : dep(
            name = "io.ktor:ktor-client-ios",
            version = Versions.ktor
        )
    }

    object Serialization : dep(
        name = "org.jetbrains.kotlinx:kotlinx-serialization-json",
        version = Versions.serialization
    )

    object Reaktive {

        object Core : dep(
            name = "com.badoo.reaktive:reaktive",
            version = Versions.reaktive
        )

        object Interop : dep(
            name = "com.badoo.reaktive:coroutines-interop",
            version = Versions.reaktiveNmtc
        )
    }

    object Kodein : dep(
        name = "org.kodein.di:kodein-di",
        version = Versions.kodein
    )

    object Settings : dep(
        name = "com.russhwolf:multiplatform-settings",
        version = Versions.settings
    )
}
