import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.iti.azzurra"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    val myProperties = Properties()
    myProperties.load(rootProject.file("local.properties").inputStream())

    val signing = signingConfigs.create("debug_and_release") {
        storeFile = rootProject.file("secret/key.jks")
        storePassword = myProperties.getProperty("KEYSTORE_PASSWORD")
        keyAlias = myProperties.getProperty("KEY_ALIAS")
        keyPassword = myProperties.getProperty("KEY_PASSWORD")
    }

    defaultConfig {
        applicationId = "com.iti.azzurra"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"${myProperties.getProperty("BASE_URL")}\"")
        buildConfigField(
            "String",
            "WEATHER_API_KEY",
            "\"${myProperties.getProperty("WEATHER_API_KEY")}\""
        )
        androidResources {
            localeFilters.addAll(setOf("en", "ar"))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signing
        }
        debug {
            signingConfig = signing
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // collect as state with lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    // datastore proto
    implementation(libs.androidx.datastore)

    // room database
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // compose nav host & type safe navigation
    implementation(libs.androidx.navigation.compose)

    // Kotlin json
    implementation(libs.kotlinx.serialization.json)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // material3 expressive
    implementation(libs.androidx.material3.android)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor3)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    //work manager
    implementation(libs.androidx.work.runtime.ktx)

    //cloudy
    implementation(libs.cloudy)
    //haze
    implementation(libs.haze)
    implementation(libs.haze.materials)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // hilt view model
    implementation(libs.androidx.hilt.navigation.compose)
    //hilt work
    implementation(libs.androidx.hilt.work)

    // appcompat
    implementation(libs.androidx.appcompat)

    //play services location
    implementation(libs.play.services.location)
}