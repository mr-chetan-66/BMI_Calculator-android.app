plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")    // For annotation processing if needed
    id("androidx.navigation.safeargs.kotlin") version "2.8.3"

}

android {
    namespace = "com.tsa.bmicalculator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tsa.bmicalculator"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true // Corrected here
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.transition:transition:1.4.1")
    implementation("com.github.bumptech.glide:glide:4.15.0")
    implementation ("com.google.android.material:material:1.10.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.fragment:fragment-ktx:1.5.9")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.1")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.1")
    implementation ("androidx.core:core-ktx:1.7.0") // or latest version
    implementation ("androidx.activity:activity-ktx:1.2.0")
    implementation ("com.airbnb.android:lottie:6.6.1")
    kapt("com.github.bumptech.glide:compiler:4.15.0")
}