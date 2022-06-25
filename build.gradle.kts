plugins {
    java
    groovy
}

group = "com.redis6"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    // https://mvnrepository.com/artifact/redis.clients/jedis
    implementation("redis.clients:jedis:3.2.0")
    testImplementation("org.spockframework:spock-core:2.1-groovy-3.0")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.11")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
