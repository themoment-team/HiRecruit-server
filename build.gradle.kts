import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    kotlin("plugin.noarg") version "1.6.21"
    kotlin("kapt") version "1.3.61"

    jacoco
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
    annotation("org.springframework.context.annotation.Configuration")
    annotation("org.springframework.stereotype.Repository")
    annotation("org.aspectj.lang.annotation.Aspect")
}

noArg {
    annotation("site.hirecruit.hr.global.annotation.Dto")
}

group = "site.hirecruit.hr"
base.archivesBaseName = "hirecruit"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_11
val qeurydslVersion = "5.0.0"

tasks.getByName<BootJar>("bootJar") {
    layered
}

repositories {
    mavenCentral()
}

dependencies {
    /** security **/
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    /** jpa, querydsl **/
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:$qeurydslVersion")
    kapt("com.querydsl:querydsl-apt:$qeurydslVersion:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    /** DB **/
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.7.0")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")

    /** for business **/
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.modelmapper:modelmapper:2.1.1")


    /** for spring app **/
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    testImplementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    /** for aws sdks **/
    // aws-java-sdk-core
    implementation("com.amazonaws:aws-java-sdk-core:1.12.223")
    // software.amazon.awssdk/sns
    implementation("software.amazon.awssdk:sns:2.17.193")
    // https://mvnrepository.com/artifact/software.amazon.awssdk/sesv2
    implementation("software.amazon.awssdk:sesv2:2.17.209")
    // https://mvnrepository.com/artifact/ca.pjer/logback-awslogs-appender
    implementation("ca.pjer:logback-awslogs-appender:1.6.0")



    /** for test **/
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.15.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("com.ninja-squad:springmockk:3.1.1")

    compileOnly("it.ozimov:embedded-redis:0.7.2")
    testImplementation("it.ozimov:embedded-redis:0.7.2")

    /** for prod **/
    implementation("me.paulschwarz:spring-dotenv:2.5.4")

    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}

/** Querydsl ??? ??????????????? Qclass ?????? ?????? **/
sourceSets["main"].withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    finalizedBy("testCoverage")
}

jacoco {
    // JaCoCo ??????
    toolVersion = "0.8.8"
}

/**
 * ???????????? ???????????? ????????? ????????? ?????? ?????? ????????? ???????????? ???????????????.
 */
tasks.jacocoTestReport {
    reports {
        // ????????? ???????????? ?????? ??? ??? ????????????.
        html.isEnabled = true
        xml.isEnabled = false
        csv.isEnabled = false

    }

    finalizedBy("jacocoTestCoverageVerification")
}

/**
 * ???????????? ????????? ??????????????? ????????? ?????? task
 */
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            // 'element'??? ????????? ??????????????? ?????? ????????? ?????? ?????? ???????????? ??????.
            limit {
                // 'counter'??? ???????????? ????????? default??? 'INSTRUCTION'
                // 'value'??? ???????????? ????????? default??? 'COVEREDRATIO'
                minimum = "0.30".toBigDecimal()
            }
        }

        rule {
            // ?????? ????????? ?????? ??? ??? ??????.
            enabled = false

            // ?????? ????????? ????????? ????????? ??????
            element = "CLASS"

            // ????????? ??????????????? ????????? 50% ??????????????? ??????.
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.50".toBigDecimal()
            }

            // ?????? ??????????????? ????????? 30% ??????????????? ??????.
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.30".toBigDecimal()
            }

            // ???????????? ????????? ????????? ????????????
            excludes = listOf(
                    "*.test.*",
                    "*.Kotlin*",
                    "**/QA",
                    "**/QZ"
            )
        }
    }
}

/**
 * ?????? Report task??? Verification task??? ????????? ?????? task ?????????. <br>
 * ????????? ????????? ?????? ?????? ?????????.
 *
 * ./gradlew tasks
 */
val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage"

    dependsOn(":test",
        ":jacocoTestReport",
        ":jacocoTestCoverageVerification")

    tasks["jacocoTestReport"].mustRunAfter(tasks["test"])
    tasks["jacocoTestCoverageVerification"].mustRunAfter(tasks["jacocoTestReport"])
}
