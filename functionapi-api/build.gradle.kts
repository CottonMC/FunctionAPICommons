import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins{
    java
    kotlin("jvm")
}

version = "1.1-SNAPSHOT"



configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val test : Test by tasks

test.useJUnitPlatform()


dependencies {
    compile("com.mojang:brigadier:1.0.17")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.5")
// https://mvnrepository.com/artifact/com.google.guava/guava
    compile("com.google.guava:guava:21.0")
    compile(kotlin("stdlib-jdk8"))

}

