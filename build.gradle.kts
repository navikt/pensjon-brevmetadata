plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
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
    testRuntimeOnly(libs.junit.platform.launcher)
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