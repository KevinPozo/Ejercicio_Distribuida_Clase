import org.gradle.kotlin.dsl.implementation

plugins {
    id("java")
    id("io.quarkus") version "3.40.1.1"
    id("io.freefair.lombok") version "9.8.3"
}

group = "com.programacion.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}
val quarkusVersion = "3.40.1.1"
dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:$quarkusVersion"))
    //CDI
    implementation("io.quarkus:quarkus-arc")
    //DI
    implementation("io.quarkus:quarkus-rest")
    //JSON B
    implementation("io.quarkus:quarkus-rest-jsonb")

    // Hibernate ORM con Panache
    implementation("io.quarkus:quarkus-hibernate-orm-panache")

    implementation("io.quarkus:quarkus-hibernate-orm")
    // JDBC Driver PostgreSQL
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    //REST CLIENT
    implementation("io.quarkus:quarkus-rest-client-jsonb")
    implementation("io.quarkus:quarkus-rest-client")
    //Service discovery
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")

}
java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.test {
    useJUnitPlatform()
}
