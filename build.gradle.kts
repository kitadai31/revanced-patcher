plugins {
    kotlin("jvm") version "1.9.20"
    `maven-publish`
    alias(libs.plugins.binary.compatibility.validator)
}

group = "app.revanced"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.xpp3)
    implementation(libs.smali)
    implementation(libs.multidexlib2)
    implementation(libs.apktool.lib)
    implementation(libs.kotlin.reflect)

    compileOnly(libs.android)

    testImplementation(libs.kotlin.test)
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "SKIPPED", "FAILED")
        }
    }

    processResources {
        expand("projectVersion" to project.version)
    }
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/revanced/revanced-patcher")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
