[![](https://jitpack.io/v/heychazza/spigot-plugin-lib.svg)](https://jitpack.io/#heychazza/spigot-plugin-lib)

# Spigot Plugin Library
A lightweight and easy to use library for fast plugin creation, built as a base for all my plugins, without the need to manually copy classes many times throughout each project.

## Features
Our library comes with a few useful additions to help with boilerplate code, and we look to expand this as time goes on.

## Using the Library

#### Maven
Add the following to your pom.xml
```xml
<repositories>
    <!-- Other repos.. -->
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Then, add the following dependency to your pom.xml
```xml
<dependency>
    <groupId>com.github.heychazza</groupId>
    <artifactId>spigot-plugin-lib</artifactId>
    <version>v1.3.8</version>
</dependency>
```

#### Gradle
Add the following to your build.gradle
```groovy
allprojects {
    repositories {
        maven {
            url 'https://jitpack.io'
        }
    }
}
```

Then, add the following dependency to your build.gradle
```groovy
dependencies {
   implementation 'com.github.heychazza:spigot-plugin-lib:v1.3.8'
}
```

## Compiling
You can compile the library by running the following command.
```bash
./gradlew shadowJar
```
