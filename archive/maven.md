# Maven

How to initialize **maven** project.

## Reference

- [Apache Maven in 5 Minute](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

## Table of Contents

- [Install Maven](#install-maven)
- [Create Project](#create-project)
- [Compile](#compile)
- [Exec Maven Plugin](#exec-maven-plugin)
  - [Terminal Command](#terminal-command)
- [Package](#package)

---

## Install Maven

### macOS

```sh
brew install maven
```

### Arch

```sh
sudo pacman -Syu maven
```

Check installation with:

```sh
mvn --version
```

---

## Create Project

```sh
mvn archetype:generate \
    -DgroupId=com.unemotioned.englishtest \
    -DartifactId=english-test \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DarchetypeVersion=1.5 \
    -DinteractiveMode=false
```

- Structure: `src/main/com/unemotioned/englishtest/App.java`

Under `com.unemotioned.englishtest`, create **Controller**, **Service**,
**Model.vo** etc.

---

## Compile

```sh
mvn compile
```

---

## Exec Maven Plugin

**_A plugin to allow execution of system and Java programs._**

1. Get it from [Maven Repository](https://mvnrepository.com/artifact/org.codehaus.mojo/exec-maven-plugin).
2. Add it to **pom.xml** inside `build.plugins` tags.
3. Inside the **&lt;plugin&gt;** add `configuration.mainClass` tags with path of
   main class.

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.6.3</version>
    <configuration>
        <mainClass>com.unemotioned.englishtest.App</mainClass>
    </configuration>
</plugin>
```

### Terminal Command

Compile first.

Then run with `exec` command:

```sh
mvn exec:java

# in one line
mvn compile exec:java
```

---

## Package

- **clean**: Remove previously built artifacts (`target` directory).
- **package**: Output JAR file under target directory.

```sh
mvn clean package
```
