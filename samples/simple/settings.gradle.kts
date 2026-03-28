
pluginManagement {
  repositories {
    mavenLocal()
    gradlePluginPortal()
    mavenCentral()
  }
  plugins {
    val bramboltVersion: String by settings
    id("com.brambolt.gradle.build.staging") version bramboltVersion
  }
}

rootProject.name = "brambolt-gradle-build-staging-sample-simple"
