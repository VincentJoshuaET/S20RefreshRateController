import com.android.builder.core.DefaultApiVersion

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion = "android-30"
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.vjet.s20refreshratecontroller"
        minSdkVersion = DefaultApiVersion(21)
        targetSdkVersion = DefaultApiVersion(21)
        versionCode = 6
        versionName = "2.4"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles = mutableListOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    File("proguard-rules.pro")
            )
        }
    }

    signingConfigs {
        register("release") {
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
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
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation("com.google.android.material:material:1.3.0")
}