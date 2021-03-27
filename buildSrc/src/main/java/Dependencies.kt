private typealias dep = LibraryDependency

object Dependencies {

    object Gradle {

        object KotlinGradlePlugin : dep(
            name = "org.jetbrains.kotlin:kotlin-gradle-plugin",
            version = Versions.kotlin
        )

        object Android: dep(
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
}
