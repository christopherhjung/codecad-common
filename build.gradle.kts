import org.gradle.kotlin.dsl.execution.ProgramText.Companion.from

plugins {
    java
    kotlin("jvm") version "1.5.21"
    `maven-publish`
}

group = "com.codecad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}


dependencies {
    implementation(kotlin("stdlib"))
}
