plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "com.vjet.s20refreshratecontroller"
        minSdkVersion(21)
        targetSdkVersion(21)
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        isCheckReleaseBuilds = false
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.android.material:material:1.2.1")
}