plugins {
    java
    kotlin("jvm") version libs.versions.kotlinVersion
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    kotlin("plugin.spring") version libs.versions.kotlinVersion
}

val javaTarget: String by System.getProperties()

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot.starter.webmvc)
    implementation(libs.io.prometheus.simpleclient.common)
    implementation(libs.net.logstash.logback.logstash.logback.encoder)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webmvc.ui)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.webmvc.test)
    testImplementation(libs.org.skyscreamer.jsonassert)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.platform.launcher)
    implementation(kotlin("stdlib"))
}

group = "no.nav"
version = "0.0.1-SNAPSHOT"
description = "pensjon-brevdata"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaTarget)
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}