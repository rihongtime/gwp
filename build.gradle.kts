import com.linecorp.support.project.multi.recipe.configure
import com.linecorp.support.project.multi.recipe.configureByTypeHaving
import com.linecorp.support.project.multi.recipe.configureByTypePrefix
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byLabel
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byTypeSuffix
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byTypeHaving
import com.linecorp.support.project.multi.recipe.matcher.ProjectMatchers.Companion.byTypePrefix

import com.linecorp.support.project.multi.recipe.matcher.and
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

java.sourceCompatibility = JavaVersion.VERSION_11

val group = "com.kidaristudio.backend"
val version = "0.0.1-SNAPSHOT"

val jasyncR2dbcMysql = "1.1.7"
val springDataR2dbc = "1.2.4"
val springData = "2.4.4"

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.linecorp.build-recipe-plugin") version "1.1.1"
    id("com.google.cloud.tools.jib") version "1.6.0"

    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("kapt") version "1.4.31"

}

allprojects {
    repositories {
        mavenCentral()
    }
}

configureByTypePrefix("kotlin") {

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "io.spring.dependency-management")

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    configure<DependencyManagementExtension> {
        dependencies {
            dependencySet("org.jetbrains.kotlinx:1.4.3") {
                entry("kotlinx-coroutines-core")
                entry("kotlinx-coroutines-reactor")
            }
            dependencySet("io.projectreactor.kotlin:1.1.3") {
                entry("reactor-kotlin-extensions")
            }
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    }
}

configure(
    byTypePrefix("kotlin") and byTypeHaving("boot")
) {
    apply(plugin = "kotlin-kapt")

    dependencies {
        "kapt"("org.springframework.boot:spring-boot-configuration-processor:2.2.4.RELEASE")
        implementation(project(":persistence:common"))
        implementation(project(":persistence:contents"))
    }
}

configureByTypeHaving("boot") {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")

    dependencies {
        implementation("org.springframework.boot:spring-boot")
        implementation("org.springframework.boot:spring-boot-starter-webflux")

        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


configure(
    byTypeSuffix("boot-application") and byLabel(
        "spring-boot-webflux"
    )
) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
        implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
        implementation("org.springframework.boot:spring-boot-starter-actuator")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("joda-time:joda-time:2.9.7")

        runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    }
}


configure(
    byTypeSuffix("lib") and byLabel("persistence")
) {
    dependencies {
        implementation("org.springframework.data:spring-data-commons:$springData")
        implementation("org.springframework.data:spring-data-r2dbc:$springDataR2dbc")
        implementation("com.github.jasync-sql:jasync-r2dbc-mysql:$jasyncR2dbcMysql")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")

        implementation("io.projectreactor.addons:reactor-pool:0.2.2")
        implementation("io.r2dbc:r2dbc-pool:0.8.5.RELEASE")
        implementation("io.r2dbc:r2dbc-spi:0.8.3.RELEASE")
    }
}

configure(byTypeHaving("boot") and byTypeSuffix("lib")) {
    tasks {
        withType<Jar> {
            enabled = true
        }

        withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
            enabled = false
        }

        withType<org.springframework.boot.gradle.tasks.run.BootRun> {
            enabled = false
        }
    }
}
