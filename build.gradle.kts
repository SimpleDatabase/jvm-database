import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "de.softwarebuilds"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "com.mysql", name = "mysql-connector-j", version = "8.3.0")
    implementation(group = "org.xerial", name = "sqlite-jdbc", version = "3.45.1.0")
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}