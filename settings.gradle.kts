pluginManagement {
    repositories {
        mavenCentral()
        google()
        jcenter()
        gradlePluginPortal()
    }
    
}
rootProject.name = "LastFM"

enableFeaturePreview("GRADLE_METADATA")

include(":androidApp")
include(":shared")
