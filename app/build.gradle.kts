plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.assignment3sqliteandsharedpreferences"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.assignment3sqliteandsharedpreferences"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        // Enable desugaring for java.time.*
        isCoreLibraryDesugaringEnabled = true
    }
}


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.kizitonwose:CalendarView:1.0.4")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Required for java.time (desugaring support)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}
