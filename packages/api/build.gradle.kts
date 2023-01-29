plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

group = "com.brys.flashnotes.api"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        url = uri("https://maven.reposilite.com/releases")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.javalin:javalin:5.3.2")
    implementation("org.mongodb:mongo-java-driver:3.12.11")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.5")
    implementation("com.github.javalin:javalin-openapi:5.3.1")
    implementation("org.litote.kmongo:kmongo:4.8.0")
    implementation("io.javalin:javalin-bundle:5.3.2")
    implementation("com.google.api-client:google-api-client:2.1.3")
    implementation("io.javalin:javalin-openapi-plugin:5.0.1")
    implementation("com.auth0:java-jwt:4.2.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
    kotlinOptions.apiVersion = "1.8"
    kotlinOptions {
        jvmTarget = "18"
    }
}
