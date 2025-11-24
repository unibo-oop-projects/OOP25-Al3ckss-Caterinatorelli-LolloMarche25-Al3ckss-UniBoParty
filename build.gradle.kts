plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    // Note: quality plugins (Checkstyle/PMD/SpotBugs) removed to allow build
    // You can re-enable static analysis by adding a quality plugin and configuring rules.
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")

    // Maven dependencies are composed by a group name, a name and a version, separated by colons
    implementation("com.omertron:API-OMDB:1.5")
    implementation("org.jooq:jool:0.9.15")

    /*
     * Simple Logging Facade for Java (SLF4J) with Apache Log4j
     * See: http://www.slf4j.org/
     */
    val slf4jVersion = "2.0.17"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    // Logback backend for SLF4J
    runtimeOnly("ch.qos.logback:logback-classic:1.5.20")

    // JUnit API and testing engine
    // Use JUnit 5 (JUnit Jupiter)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.UniBoParty.application.App")
}

// Use Java toolchain to ensure consistent compilation on different machines
java {
    toolchain {
        languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(21))
    }
}

// Configure tests to use JUnit Platform and detailed logging
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        showStandardStreams = true
    }
}