plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.vjet.s20refreshratecontroller"
        minSdkVersion(21)
        targetSdkVersion(21)
        versionCode = 5
        versionName = "2.3"
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
    implementation(kotlin("stdlib-jdk7", "1.4.21"))
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation("com.google.android.material:material:1.2.1")
}