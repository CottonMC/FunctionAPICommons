plugins {
    java
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

dependencies {
    compile("com.mojang:brigadier:1.0.17")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.5")
// https://mvnrepository.com/artifact/com.google.guava/guava
    compile("com.google.guava:guava:21.0")

    testCompile("org.junit.jupiter:junit-jupiter-api:5.5.2")

    testCompile("org.junit.platform:junit-platform-commons:1.5.2")
    testCompile("org.junit.platform:junit-platform-engine:1.5.2")
    testCompile("org.mockito:mockito-junit-jupiter:3.0.0") {
        exclude(group=("org.junit.jupiter"))
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}