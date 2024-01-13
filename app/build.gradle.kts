plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.nitishsharma.swipemart"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }


    defaultConfig {
        applicationId = "com.nitishsharma.swipemart"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://app.getswipe.in/\"")
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
        debug {
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //epoxy
    implementation("com.airbnb.android:epoxy:4.4.2")
    implementation("com.airbnb.android:epoxy-databinding:4.4.2")
    kapt("com.airbnb.android:epoxy-processor:4.4.0")

    //networking
    val retrofit_version = "2.9.0"
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.github.bumptech.glide:glide:4.12.0")

    //logging on networking calls
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //swipe refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //recycler and card view
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    //navigation
    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //mvvm
    val mvvm_version = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$mvvm_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$mvvm_version")

    //koin
    val koin_version = "3.4.0"
    implementation("io.insert-koin:koin-android:$koin_version")

    //timber logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    //butterknife
    implementation("com.jakewharton:butterknife:10.2.3")
    kapt("com.jakewharton:butterknife-compiler:10.2.3")

    //facebook shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    //splash api
    implementation("androidx.core:core-splashscreen:1.0.1")

}