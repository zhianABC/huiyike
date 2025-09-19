plugins {
    java
    kotlin("jvm") version "1.9.25" apply false
}
allprojects {
    group = "com.huiyike"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}