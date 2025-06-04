import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    //linuxX64()
    //macosX64()
    mingwX64()
    jvm("jna")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(rootProject)
            }
        }

        val jnaMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }

        all {
            languageSettings.optIn("kotlin.ExperimentalUnsignedTypes")
        }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries {
            executable("pcsc_sample")
        }
    }
}

// Файл jar должен быть настроен как отдельная задача, без изменения зависимостей
tasks.register<Jar>("jnaFatJar") {
    dependsOn("jnaJar")
    group = "jar"
    manifest.attributes["Main-Class"] = "SampleKt"

    val runtimeClasspath = configurations.named("jnaRuntimeClasspath").get()
    val jnaJar = tasks.named("jnaJar").get().outputs.files

    from(runtimeClasspath.map { zipTree(it) } + jnaJar.map { zipTree(it) })

    exclude("META-INF/versions/9/module-info.class")
}

// Настройка воспроизводимости (можно оставить в afterEvaluate)
tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
