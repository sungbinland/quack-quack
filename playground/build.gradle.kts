/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

plugins {
    id(ConventionEnum.AndroidApplication)
    id(ConventionEnum.AndroidApplicationCompose)
    id(ConventionEnum.JvmDokka)
    id(libs.plugins.oss.license.get().pluginId)
}

android {
    namespace = "team.duckie.quackquack.playground"

    val fileConfigurationFile = File(
        "$rootDir/buildSrc/src/main/kotlin/BuildConstants.kt",
    ).also {
        println("File: ${it.absolutePath}")
        println("Exists: ${it.exists()}")
    }
    if (fileConfigurationFile.exists()) {
        signingConfigs {
            create("release") {
                storeFile = file(BuildConstants.StoreFilePath)
                storePassword = BuildConstants.StorePassword
                keyAlias = BuildConstants.KeyAlias
                keyPassword = BuildConstants.KeyPassword
            }
        }

        buildTypes {
            release {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }

    lint {
        // 플레이그라운드용 데모 컴포저블에 주석을 필수로 명시하는건 너무 과함
        disable.add("KDocFields")
    }
}

dependencies {
    implementations(
        libs.ktx.core,
        libs.util.oss.license,
        libs.util.systemuicontroller,
        libs.compose.material3,
        libs.androidx.appcompat,
        libs.androidx.splash,
        libs.compose.material,
        libs.androidx.datastore,
        libs.kotlin.collections.immutable,
        projects.uiComponents,
        projects.uxWritingRule,
        projects.uxWritingModel,
        projects.uxWritingOverlay,
    )
    lintChecks(projects.lintCore)
    lintChecks(projects.lintCompose)
}
