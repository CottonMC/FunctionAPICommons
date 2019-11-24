plugins {
    java
    idea
    id("com.jfrog.artifactory") version "4.9.0"

}

group = "io.github.cottonmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://libraries.minecraft.net")
    }
    maven {
        setUrl("http://server.bbkr.space:8081/artifactory/libs-release")
    }
    maven {
        setUrl("https://libraries.minecraft.net")
    }
}

allprojects{
    apply(plugin="java")
    apply(plugin="maven-publish")
    apply(from=project.rootProject.rootDir.absolutePath+"/publishing.gradle")
}

dependencies {
    compile("com.mojang:brigadier:1.0.17")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.5")
// https://mvnrepository.com/artifact/com.google.guava/guava
    compile("com.google.guava:guava:21.0")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8

}

