//plugins {
//    id("com.android.application") version "4.2.0-alpha08" // apply false
//    kotlin("android") version "1.4.0" // apply false
//}

val kotlinVersion: String by project
println("err $kotlinVersion")
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-alpha08")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0")
    }
    repositories {
        google()
        jcenter()
    }
}

allprojects {
    repositories {
//        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap/")
        google()
        jcenter()
    }
}
