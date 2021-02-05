/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import br.com.zup.beagle.Dependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("de.mannodermaus.android-junit5")

}
apply {
    from(File("./../jacoco-android.gradle"))
}

android {
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion(Dependencies.Versions.buildTools)
    testOptions.unitTests.isIncludeAndroidResources = true

    defaultConfig {
        minSdkVersion(Dependencies.Versions.minSdk)
        targetSdkVersion(Dependencies.Versions.targetSdk)

        versionCode(Dependencies.Releases.versionCode)
        versionName(Dependencies.Releases.beagleVersionName)

        testInstrumentationRunner(Dependencies.GeneralNames.testInstrumentationRunner)
        consumerProguardFiles(Dependencies.GeneralNames.consumerProguard)
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true

        unitTests.all {
            it.useJUnitPlatform()
        }
    }

}

dependencies {
    implementation(Dependencies.AndroidxLibraries.appcompat)
    implementation(Dependencies.AndroidxLibraries.coreKtx)
    implementation(Dependencies.AndroidxLibraries.recyclerView)
    implementation(Dependencies.AndroidxLibraries.viewModel)
    implementation(Dependencies.AndroidxLibraries.viewModelExtensions)

    implementation(Dependencies.GeneralLibraries.kotlin)
    implementation(Dependencies.GeneralLibraries.kotlinCoroutines)

    api(project(Dependencies.Modules.androidAnnotation))

    kapt(project(Dependencies.Modules.internalProcessor))

    implementation(Dependencies.GeneralLibraries.yoga)
    implementation(Dependencies.GeneralLibraries.soLoader)
    implementation(Dependencies.GeneralLibraries.jni)

    implementation(Dependencies.GoogleLibraries.materialDesign)

    implementation(Dependencies.MoshiLibraries.moshi)
    implementation(Dependencies.MoshiLibraries.kotlin)
    implementation(Dependencies.MoshiLibraries.adapters)

    // Unit Testing
    testImplementation(Dependencies.TestLibraries.kotlinCoroutinesTest)
    testImplementation(Dependencies.TestLibraries.mockk)
    testImplementation(Dependencies.TestLibraries.archCoreTesting)
    testImplementation(Dependencies.GeneralLibraries.jsonObject)

    testImplementation(Dependencies.TestLibraries.junitApi)
    testImplementation(Dependencies.TestLibraries.junitEngine)
    testImplementation(Dependencies.TestLibraries.junit4)
    testImplementation(Dependencies.TestLibraries.junitVintageEngine)

    testImplementation(Dependencies.TestLibraries.testRunner)
    testImplementation(Dependencies.TestLibraries.testExt)
    testImplementation(Dependencies.TestLibraries.espressoCore)
    testImplementation(Dependencies.TestLibraries.testCore)
    testImplementation(Dependencies.TestLibraries.testRules)
    testImplementation(Dependencies.TestLibraries.robolectric)
}

apply {
    from(File("./../../maven-publish.gradle"))
}
/**
 *  https://github.com/cotechde/dokka-hugo-plugin
 *  To run this task by ./gradlew dokkaHugo
 */

tasks.register<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHugo") {
    dependencies {
        plugins("com.github.cotechde:dokka-hugo-plugin:2.0")
    }
    pluginConfiguration<org.jetbrains.dokka.hugo.HugoPlugin, org.jetbrains.dokka.hugo.HugoConfiguration> {
        titleReplace = hashMapOf("br.com.zup.beagle.android." to "", "br.com.zup.beagle." to "", "." to " ")
        titleCapitalize = true
        linkTitleReplace = hashMapOf("br.com.zup.beagle.android." to "", "br.com.zup.beagle." to "", "." to " ")
        linkTitleCapitalize = true
    }
}


