import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    java
    id("org.springframework.boot") version "3.5.8"
    id("org.openapi.generator") version "7.17.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

var openApiGeneratedSourceDir = layout.buildDirectory.dir("generated/openapi").get()
sourceSets {
    main {
        java {
            srcDir(openApiGeneratedSourceDir.dir("src/main/java"))
        }
    }
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot BOM
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.8"))

    // Inter-module Dependencies
    implementation(project(":acore-common"))
    implementation(project(":acore-dbc"))
    implementation(project(":acore-antlr"))
    implementation(project(":acore-ui"))

    // Dependencies provided by the Spring Boot BOM
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("com.mysql:mysql-connector-j")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.hibernate.validator:hibernate-validator")

    // Manual dependencies not specified in the Spring Boot BOM
    implementation("org.apache.commons:commons-csv:1.14.1")

    // Test Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

var openApiSpec = layout.projectDirectory.dir("src/main/resources/static/openapi.yaml")
openApiGenerate {
    generatorName = "spring"
    inputSpec = openApiSpec.toString()
    outputDir = openApiGeneratedSourceDir.toString()
    apiPackage = "net.kwas.acore.server.api"
    invokerPackage = "net.kwas.acore.server.invoker"
    modelPackage = "net.kwas.acore.server.model"
    configOptions = mapOf(
        "dateLibrary" to "java8",
        "library" to "spring-boot",
        "useSpringBoot3" to "true",
        "interfaceOnly" to "true",
        // Using OpenAPI's nullable requires another dependency.
        // Turn it back on if I need it.
        "openApiNullable" to "false",
        // The yaml spec IS my documentation.
        "documentationProvider" to "none",
        // Response entities add a bit more complexity (and power) that I don't need right now.
        // Start using them if I need the flexibility.
        "useResponseEntity" to "false",
        // This makes it easier to know what methods I don't have implemented.
        "skipDefaultInterface" to "true"
    )
}

openApiValidate {
    inputSpec = openApiSpec.toString()
}
