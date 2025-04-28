import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	java
	id("org.springframework.boot") version "3.4.3"
}

group = "net.kwas.acore"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.3"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("com.mysql:mysql-connector-j")
    implementation("net.kwas.acore:acore-dbc:0.0.1-SNAPSHOT")

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

tasks.processResources {
    dependsOn(":syncUiToServer")
}
