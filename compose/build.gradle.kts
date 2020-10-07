plugins {
    id("com.android.application") // version "4.2.0-alpha08" apply false

    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")

    kotlin("android") // version "1.4.0" apply false
    kotlin("android.extensions")
    kotlin("kapt")
}

val composeVersion = "1.0.0-alpha03"
val coroutinesVersion = "1.3.9"
val lifecycle_version = "2.2.0"

dependencies {

    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("com.google.android.material:material:1.2.0")

    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")

    // https://developer.android.com/jetpack/androidx/releases/fragment
    implementation("androidx.fragment:fragment-ktx:1.2.5")

    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version") // ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version") // LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")

    implementation("androidx.ui:ui-tooling:$composeVersion")

    implementation ("com.jakewharton.timber:timber:4.7.1")

    // DI
    implementation ("com.github.stephanenicolas.toothpick:ktp:3.1.0")
    implementation ("com.github.stephanenicolas.toothpick:smoothie-androidx:3.1.0")
    implementation ("com.github.stephanenicolas.toothpick:smoothie-lifecycle-ktp:3.1.0")
    implementation ("com.github.stephanenicolas.toothpick:smoothie-lifecycle-viewmodel-ktp:3.1.0")
    kapt ("com.github.stephanenicolas.toothpick:toothpick-compiler:3.1.0")

    // RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1") // https://github.com/ReactiveX/RxAndroid/blob/2.x/CHANGES.md
    implementation("io.reactivex.rxjava2:rxkotlin:2.2.0") // https://github.com/ReactiveX/RxKotlin/releases
    implementation("com.jakewharton.rxrelay2:rxrelay:2.1.0") // https://github.com/JakeWharton/RxRelay/blob/master/CHANGELOG.md

    // Networking
    implementation("com.squareup.picasso:picasso:2.71828") // https://github.com/square/picasso

    // Firebase
    implementation("com.google.firebase:firebase-analytics-ktx:17.5.0")
    implementation("com.google.firebase:firebase-auth-ktx:19.4.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:17.2.2")
    implementation("com.google.firebase:firebase-database-ktx:19.5.0")

    testImplementation("junit:junit:4.13")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.0"

    defaultConfig {
        applicationId("com.kanawish.recordstore")
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerVersion = "1.4.0"
        kotlinCompilerExtensionVersion = composeVersion
    }
    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check", "-Xskip-metadata-version-check")
    }
}