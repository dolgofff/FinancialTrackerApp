plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation (project(":domain"))

    //Hilt dependencies
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

    //Room dependencies
    api(libs.room.runtime)
    implementation(libs.androidx.room)
    ksp(libs.room.compiler)

    //Retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.ser)

    //OkHttp dependencies
    implementation(libs.okhttp.logging)
    implementation(libs.okhttp)

    //Serialization JSON dependency
    implementation(libs.kotlinx.serialization.json)

    //Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}