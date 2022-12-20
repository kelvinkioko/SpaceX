// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
    }
}

plugins {
    id("com.android.application") version("7.3.1") apply false
    id("com.android.library") version("7.3.1") apply false
    kotlin("android") version("1.7.0") apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
