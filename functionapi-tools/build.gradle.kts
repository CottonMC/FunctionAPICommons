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
    compile(project(":functionapi-api"))
    testCompile("junit", "junit", "4.12")
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}