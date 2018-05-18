import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.extra
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

group = "xctest_parser"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath(Plugins.KOTLIN)
    }
}

plugins {
    application
    jacoco
    kotlin("jvm") version Versions.KOTLIN
}

// http://www.eclemma.org/jacoco/
jacoco {
    toolVersion = "0.8.0"
}

// Html report created in kotlin_poc/build/reports/jacoco/test/html/index.html
// ./gradlew clean check jacocoTestReport
tasks.withType<JacocoReport> {
    reports {
        html.isEnabled = true
    }
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

apply {
    plugin("kotlin")
}

application {
    mainClassName = "main"
}

repositories {
    jcenter()
}

// Output full test results to console
// Avoids having to read the HTML report
tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
    }
}

dependencies {
    compile(Libs.KOTLIN_STD_LIB)

    testImplementation("junit:junit:4.12")
}

val javaVersion = "1.8"
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = javaVersion
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = javaVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}