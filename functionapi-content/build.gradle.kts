import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")

}

group = "io.github.cottonmc"
version = "1.4"


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


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val test: Test by tasks
test.useJUnitPlatform()


dependencies {
    implementation("com.mojang:brigadier:1.0.17")

    compile(project(":functionapi-api"))
    testCompile("junit", "junit", "4.12")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.5")

    compile("org.jetbrains.kotlin:kotlin-reflect:1.3.50")

    testCompile("org.junit.jupiter:junit-jupiter-api:5.5.2")

    testCompile("org.junit.platform:junit-platform-commons:1.5.2")
    testCompile("org.junit.platform:junit-platform-engine:1.5.2")
    implementation(kotlin("stdlib-jdk8"))
    testCompile("org.mockito:mockito-junit-jupiter:3.0.0") {
        exclude(group = ("org.junit.jupiter"))
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testCompile("org.junit.jupiter:junit-jupiter-params:5.4.2")
    implementation("com.google.guava:guava:21.0")

    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation(group= "com.google.code.gson", name= "gson", version= "2.8.0")

}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

apply(from=project.rootProject.rootDir.absolutePath+"/publishing.gradle")
