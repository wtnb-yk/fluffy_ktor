val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.0"
    id("org.openapi.generator") version "6.2.0"
}

group = "com.fluffy"
version = "0.0.1"
application {
    mainClass.set("com.fluffy.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-openapi:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// TODO: interfaceOnly で作成されるように修正
openApiGenerate {
    generatorName.set("kotlin-server")
    inputSpec.set("$projectDir/_build/openapi.yaml")
    outputDir.set("$buildDir/openapi/server-code/")
    apiPackage.set("com.fluffy.openapi.generated.controller")
    modelPackage.set("com.fluffy.openapi.generated.model")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "library" to "ktor",
        )
    )
}

/**
 * Kotlinをコンパイルする前に、generateApiServerタスクを実行
 * 必ずスキーマファイルから最新のコードが生成され、もし変更があったらコンパイル時に失敗して気付けるため
 */
//tasks.compileKotlin {
//    dependsOn("openApiGenerate")
//}

///**
// * OpenAPI Generator によって生成されたコードを import できるようにする
// */
//kotlin.sourceSets.main {
//    kotlin.srcDir("$buildDir/openapi/server-code/src/main")
//}
