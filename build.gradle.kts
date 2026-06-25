plugins {
    java
}

group = "com.diazaryanta"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:7.10.2")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.21.0")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:json-path:5.4.0")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    testImplementation("com.aventstack:extentreports:5.1.1")
}

tasks.test {
    useTestNG() {
        val suiteXml = System.getProperty("suiteXmlFile")
        if (suiteXml != null) {
            suites(suiteXml)
        }
    }

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}