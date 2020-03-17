
plugins {
    java
    idea
    id("com.jfrog.artifactory") version "4.13.0" apply false
    kotlin("jvm") version "1.3.61"
}


group = "io.github.cottonmc"
version = "1.0"

subprojects{
    apply(plugin="java")
    apply(plugin="idea")
    apply(plugin="maven-publish")
    apply(plugin="com.jfrog.artifactory")

    group = "io.github.cottonmc"

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
    
    dependencies{
        testCompile("org.junit.jupiter:junit-jupiter-api:5.5.2")

        testCompile("org.junit.platform:junit-platform-commons:1.5.2")
        testCompile("org.junit.platform:junit-platform-engine:1.5.2")
        implementation(kotlin("stdlib-jdk8"))
        testCompile("org.mockito:mockito-junit-jupiter:3.0.0") {
            exclude(group=("org.junit.jupiter"))
        }
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    }
   


    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

}

