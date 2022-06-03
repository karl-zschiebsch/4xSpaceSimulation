# 4xSpaceSimulation

4xSpaceSimulation is a library based on JavaFX that comes with explore, expand, exploit and exterminate features. 

## Requirements

- Java must be installed
    - if you don't have installed it yet, you can download it [here](https://www.oracle.com/java/technologies/downloads/)
- Maven should be installed
    - if you don't have installed it yet, you can download it [here](https://maven.apache.org/install.html)

## Get Started

There are two ways to use this project. You can download and run it or use it as a dependency.

### Download and run it with maven

Install it
```git
git clone https://github.com/karl-zschiebsch/4xSpaceSimulation.git
cd (...)
git install
```
and run it with the ``exec-maven-plugin``:
```git
git exec:java
```

### Run it without maven

If you want to run it without maven, you can just double-click on the .jar-File in the ``target`` folder or use the command below.
```cmd
java -jar  target\tactical-space-simulator-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
Please note that you can only run the .jar **WITH** dependencies!

### Use it as a maven dependency

```xml
<dependency>
    <groupId>org.tss</groupId>
    <artifactId>tactical-space-simulator</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```