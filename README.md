# AlgaT

<img alt="AlgaT Logo" src="src/main/resources/static/logo.png" width="256" />

AlgaT is an algorithm visualizer capable of displaying, self-running and
freezing at arbitrary points classical algorithms by means of a graphical user
interface, allowing user interaction.

## Usage
AlgaT is targeted for Java 12, relies on Java FX and can be run by
[downloading the precompiled .jar file](
https://github.com/newnone/AlgaT/wiki/Downloads) and issuing

```
java -jar <downloaded .jar path>
```

on a console screen. Alternatively, you can launch it with Gradle

```
./gradlew run
```

## Testing
AlgaT provides a unit testing suite, written in JUnit 5. It is easily runnable
through Gradle with

```
./gradlew test
```
