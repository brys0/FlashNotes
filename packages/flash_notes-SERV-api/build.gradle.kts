plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("plugin.serialization") version org.gradle.kotlin.dsl.embeddedKotlinVersion
    id("com.google.cloud.tools.jib") version "3.2.0"
}

group = "com.brys.flashnotes.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.javalin:javalin:4.5.0")
    implementation("org.mongodb:mongo-java-driver:3.12.11")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("io.javalin:javalin-openapi:4.5.0")
    implementation("org.litote.kmongo:kmongo:4.5.1")
    implementation("io.javalin:javalin-bundle:4.5.0")
    implementation("com.google.api-client:google-api-client:1.34.1")


}