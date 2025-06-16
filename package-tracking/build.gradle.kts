plugins {
	kotlin("jvm") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
	id("org.springframework.boot") version "4.0.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
//	id("org.flywaydb.flyway") version "9.16.3"
}

group = "com.sira"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.apache.kafka:kafka-streams")
	implementation("com.twilio.sdk:twilio:9.2.1")
//	implementation("org.flywaydb:flyway-core")
//	implementation("org.flywaydb:flyway-database-postgresql")

//	implementation("org.flywaydb:flyway-core:6.4.3")
//	implementation("in.hocg.boot:flyway-spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//	compile "com.fasterxml.jackson.core:jackson-annotations"
//	compile "com.fasterxml.jackson.core:jackson-core"
//	compile "com.fasterxml.jackson.core:jackson-databind"
//	runtime "com.fasterxml.jackson.datatype:jackson-datatype-jdk8"
//	runtime "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder = "paketobuildpacks/builder-jammy-base:latest"
}
