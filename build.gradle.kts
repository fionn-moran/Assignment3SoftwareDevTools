import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.6.10"
    jacoco
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    application
}

group = "me.fionnmoran"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // dependencies for logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.6")

    // For Streaming to XML
    implementation("com.thoughtworks.xstream:xstream:1.4.20")

    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.6.10")
}

tasks.test {
    useJUnitPlatform()
    // report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "MainKt"
    // for building a fat jar - include all dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}


