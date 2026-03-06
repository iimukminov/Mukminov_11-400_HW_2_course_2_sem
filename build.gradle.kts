plugins {
    id("java")
    id("application")
    id("war")
}

group = "ru.kpfu.itis"
version = "1.0-SNAPSHOT"

val springVersion: String by project
val jakartaVersion: String by project
val hibernateVersion: String by project
val postgresqlVersion: String by project
val freemarkerVersion: String by project
val hikaricpVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("org.springframework:spring-jdbc:$springVersion")
    implementation("org.springframework:spring-orm:$springVersion")
    implementation("org.springframework:spring-context-support:$springVersion")
    implementation("jakarta.servlet:jakarta.servlet-api:$jakartaVersion")
    implementation("org.hibernate.orm:hibernate-core:$hibernateVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    implementation("org.freemarker:freemarker:$freemarkerVersion")
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
}

tasks.test {
    useJUnitPlatform()
}

application {
   mainClass = "JavaSourceMerger"
}