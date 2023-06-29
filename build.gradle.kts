plugins {
    java
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	id("eclipse") //someone in stackoverflow said to add this; didnt affect result
}

repositories {
	mavenCentral()
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

	//working line
	//implementation("com.linecorp.bot:line-bot-webhook:7.2.0")
	//implementation("com.linecorp.bot:line-bot-spring-boot-handler:7.2.0")
	//implementation("com.linecorp.bot:line-bot-messaging-api-client:7.2.0")
	implementation("com.linecorp.bot:line-bot-model:6.0.0")
	implementation("com.linecorp.bot:line-bot-api-client:6.0.0")
	implementation("com.linecorp.bot:line-bot-spring-boot:6.0.0")
	
	
	
	
	
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}