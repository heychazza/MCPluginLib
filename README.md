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
    <groupId>com.github.spigot-plugins</groupId>
    <artifactId>spigot-plugin-library</artifactId>
    <version>master-SNAPSHOT</version>
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
    compile 'com.github.heychazza:spigot-plugin-lib:master-SNAPSHOT'
}
```

## Compiling
You can compile the library by running the following command.
```bash
./gradlew shadowJar
```
