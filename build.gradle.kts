import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "2.1.21"
    id("org.jetbrains.dokka") version "1.5.30"
    id("maven-publish")
}

group = "au.id.micolous.kotlin-pcsc"
version = "0.0.1"

repositories {
    mavenCentral()
}

val coroutinesVer = "1.10.1"

kotlin {
    jvm("jna") {
        compilations["main"].defaultSourceSet {
            dependencies {
                implementation("net.java.dev.jna:jna:5.9.0")
            }
        }
    }
    sourceSets.all {
        languageSettings.optIn("kotlin.ExperimentalStdlibApi")
    }

    //linuxX64()
    //macosX64()
    mingwX64 {
        compilations.getByName("main") {
            cinterops {
                maybeCreate("winscard")
            }
        }
    }
    /*targets.withType<KotlinNativeTarget>().configureEach {
        compilations["main"].cinterops {
            maybeCreate("winscard")
        }
    }*/

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVer")
            }
            languageSettings.optIn("kotlin.ExperimentalStdlibApi")
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        nativeMain {
            //dependsOn(sourceSets["commonMain"])
        }
        /*val nativeMacosMain by creating {
            dependsOn(nativeMain)
        }*/

       /* val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
        val macosX64Main by getting {
            dependsOn(nativeMacosMain)
        }*/
        mingwX64Main {
            //dependsOn(nativeWindowsMain)
        }
    }
}

/*publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            groupId = "au.id.micolous.kotlin-pcsc"
            artifactId = "pcsc"
            version = "0.0.1"
        }
    }
}*/

tasks.withType<DokkaTask>().configureEach {
    outputDirectory.set(buildDir.resolve("dokka"))

    dokkaSourceSets {
        named("commonMain") {
            includeNonPublic.set(false)
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            includes.from("src/module.md")
            sourceRoots.from(file("src/commonMain/kotlin"))
            platform.set(org.jetbrains.dokka.Platform.common)
            perPackageOption {
                matchingRegex.set("au\\.id\\.micolous\\.kotlin\\.pcsc\\.(jna|internal|native)(\$|\\.).*")
                suppress.set(true)
            }
        }

        configureEach {
            suppress.set(name != "commonMain")
        }
    }
}

afterEvaluate {
    tasks.withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}
