plugins {
    application
    kotlin("jvm") version libs.versions.kotlinVersion
}

val javaTarget: String by System.getProperties()

application {
    mainClass.set("no.nav.pensjonbrevdata.PensjonBrevdataApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.metrics)
    implementation(libs.ktor.serialization.jackson)
    implementation(libs.ktor.server.callId)
    implementation(libs.ktor.server.callLogging)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.status.pages)

    implementation(libs.io.prometheus.simpleclient.common)
    implementation(libs.net.logstash.logback.logstash.logback.encoder)

    implementation(kotlin("stdlib"))

    testImplementation(libs.org.skyscreamer.jsonassert)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.ktor.server.test.host)
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
    build {
        dependsOn(installDist)
    }
}