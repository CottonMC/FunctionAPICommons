
plugins{
    kotlin("jvm")
}

group= "io.github.cottonmc"

version = "1.1"

dependencies {
    implementation(project(":functionapi-api"))
    implementation("com.mojang:brigadier:1.0.17")

}

apply(from=project.rootProject.rootDir.absolutePath+"/publishing.gradle")
