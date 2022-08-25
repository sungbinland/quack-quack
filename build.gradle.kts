/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 8. 14. 오전 12:49
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import java.text.SimpleDateFormat
import java.util.Date
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)
}

koverMerged {
    enable()
    htmlReport {
        reportDir.set(file("$rootDir/report/test-coverage"))
    }
    xmlReport {
        reportFile.set(file("$rootDir/report/test-coverage/report.xml"))
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.build.gradle)
        classpath(libs.build.kotlin)
        classpath(libs.build.dokka.base)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    afterEvaluate {
        detekt {
            buildUponDefaultConfig = true
            toolVersion = libs.versions.detekt.get()
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.OptIn",
                    "-opt-in=kotlin.RequiresOptIn",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$rootDir/report/compose-metrics",
                )
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$rootDir/report/compose-reports",
                )
            }
        }
    }

    if (pluginManager.hasPlugin(rootProject.libs.plugins.dokka.get().pluginId)) {
        tasks.dokkaHtmlMultiModule.configure {
            moduleName.set("Duckie-QuackQuack")
            outputDirectory.set(file("$rootDir/documents/dokka"))

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                footerMessage =
                    """made with <span style="color: #ff8300;">❤</span> by <a href="https://duckie.team/">Duckie</a>"""
                customAssets = listOf(file("assets/logo-icon.svg"))
            }
        }
    }

    apply {
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }
}

subprojects {
    // https://github.com/gradle/gradle/issues/4823#issuecomment-715615422
    @Suppress("UnstableApiUsage")
    if (
        gradle.startParameter.isConfigureOnDemand &&
        buildscript.sourceFile?.extension?.toLowerCase() == "kts" &&
        parent != rootProject
    ) {
        generateSequence(parent) { project ->
            project.parent.takeIf { parent ->
                parent != rootProject
            }
        }.forEach { project ->
            evaluationDependsOn(project.path)
        }
    }

    configure<KtlintExtension> {
        version.set(rootProject.libs.versions.ktlint.source.get())
        android.set(true)
        outputToConsole.set(true)
        additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    }
}

tasks.register("cleanAll", Delete::class) {
    allprojects.map { it.buildDir }.forEach(::delete)
}

tasks.register("printGenerateSnapshotFiles") {
    println("Snapshot files generated by Paparazzi:")
    println(" ${"-".repeat(50)}")
    File("$rootDir/ui-components/src/test/snapshots/images").walk().forEach { file ->
        if (file.path.endsWith("png")) {
            val snapshotShortName = file.path.substringAfterLast("_")
            println("| $snapshotShortName")
        }
    }
    println(
        """
 ${"-".repeat(50)}
      \   ^__^
       \  (oo)\_______
          (__)\       )\/\
              ||----w |
              ||     ||
        """.replaceFirst("\n", "")
    )
}

tasks.register("configurationTestCoverageHtmlReport") {
    val rootFolderPath = "$rootDir/report/test-coverage"
    File(rootFolderPath).let { rootFolder ->
        if (!rootFolder.exists()) {
            rootFolder.mkdirs()
        }
    }

    val cname = File("$rootFolderPath/CNAME")
    val readme = File("$rootFolderPath/README.md")

    cname.writeText("quack-test.duckie.team")
    readme.writeText(
        """
        ## duckie-quack-test-coverage-deploy

        [duckie-quack-quack](https://github.com/sungbinland/duckie-quack-quack)의 테스트 커버리지에 변동이 생기면 자동으로 푸시되고 커버리지 리포트가 깃허브 페이지로 배포됩니다.

        배포 주소: [quack-test.duckie.team](https://quack-test.duckie.team)

        ---

        #### 자동 배포

        [duckie-quack-quack](https://github.com/sungbinland/duckie-quack-quack)에 `test` 와 `deploy` label 이 붙은 PR 이 올라오면 [Android CI](https://github.com/sungbinland/duckie-quack-quack/blob/develop/.github/workflows/android-ci.yml) 과정을 거치면서 이 저장소로 [Kover](https://github.com/Kotlin/kotlinx-kover)에 의해 생성된 테스트 커버리지 HTML 리포트가 자동 푸시됩니다. 이후 푸시된 파일들을 기준으로 깃허브 페이지 빌드가 시작됩니다.
        """.trimIndent()
    )
    cname.createNewFile()
    readme.createNewFile()
}

// team.duckie.quackquack.ui_Toggle_QuackToggle[1.color,2.typography]_[color:orange]-[textstyle:small].png
// [테스트 폴더 패키지명]_[테스트 클래스명]_[테스트 함수명][ParameterizedTest label]_[Paparazzi#snapshot 의 name 인자]

val String.packageName get() = split("_").first()
val String.className get() = split("_")[1]
val String.functionName get() = split("_")[2].split("[").first()
val String.parameterizedValues get() = split("_").last().removeSuffix(".png").replace(":", ": ")

fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("yyyy.MM.dd HH:mm:ss 에 생성됨")
    return formatter.format(Date())
}

/**
 * 스냅샷 이미지 HTML 리포트 생성 규칙
 *
 * - README.md 에는 클래스명만 리스트업
 * - 테스트 클래스명으로 개별 MD 파일 생성
 * - 개별 테스트 클래스명 파일 안에 테스트 함수명으로 개별 이미지 세션 추가
 * - 개별 이미지 세션에 함수명과 ParameterizedValues 를 label 로 기재
 */
tasks.register("configurationUiComponentsSnapshotDeploy") {
    try {
        val rootFolderPath = "$rootDir/ui-components/src/test/snapshots/images"
        val rootFolder = File(rootFolderPath)
        if (!rootFolder.exists()) {
            rootFolder.mkdirs()
        }

        val cname = File("$rootFolderPath/CNAME")
        val readme = File("$rootFolderPath/README.md")
        val snapshots = (rootFolder.list() ?: emptyArray()).filter { file ->
            file.endsWith("png")
        }
        val snapshotClassNameMapWithSnapshotListMdContents = snapshots.groupBy { snapshot ->
            snapshot.className
        }.map { snapshotMap ->
            val (className, _snapshots) = snapshotMap
            val snapshotContents = _snapshots.joinToString("\n\n") { snapshot ->
                val snapshotName = snapshot.functionName
                val snapshotPath = snapshot.replace(" ", "%20")
                // original: [color: orange]-[textstyle: small]
                // [transformed]
                // - color: orange
                // - textstyle: small
                val snapshotParameterizedValuesList = snapshot.parameterizedValues
                    .split("-").joinToString("\n") { value ->
                        value.replace("[", "- ").replace("]", "")
                    }
                val snapshotMdLabel = """
                    |### [$snapshotName]

                    |$snapshotParameterizedValuesList
                    """.trimMargin()
                val snapshotContent = """
                    |$snapshotMdLabel

                    |<a href="$snapshotPath"><img src="$snapshotPath" width="50%"/></a>
                    """.trimMargin()
                snapshotContent
            }
            className to snapshotContents
        }

        val snapshotTypeListMdContent = snapshotClassNameMapWithSnapshotListMdContents.joinToString(
            separator = "\n",
            prefix = "# Duckie Quack-Quack UI 스냅샷\n\n",
            postfix = "\n\n#### ${getCurrentDate()}",
        ) {
            val snapshotClassName = it.first
            "- [$snapshotClassName]($snapshotClassName.md)"
        }
        snapshotClassNameMapWithSnapshotListMdContents.forEach { (className, snapshotListMdContent) ->
            File("$rootFolderPath/$className.md").run {
                writeText("""
                    |# $className

                    |$snapshotListMdContent

                    |#### [🏠](README.md)
                """.trimMargin())
                createNewFile()
            }
        }

        cname.writeText("quack-ui.duckie.team")
        readme.writeText(snapshotTypeListMdContent)
        cname.createNewFile()
        readme.createNewFile()
    } catch (exception: IndexOutOfBoundsException) {
        println("스냅샷 이미지 HTML 리포트 생성에 실패했습니다. 어긋난 네이밍 규칙이 없는지 확인해 주세요.\n\n${exception.message}")
    }
}

apply(from = "gradle/projectDependencyGraph.gradle")
