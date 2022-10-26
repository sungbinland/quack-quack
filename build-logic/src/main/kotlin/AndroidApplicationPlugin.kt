/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
)

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import team.duckie.quackquack.convention.ApplicationConstants
import team.duckie.quackquack.convention.PluginEnum
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.configureApplication
import team.duckie.quackquack.convention.getPlaygroundVersion
import team.duckie.quackquack.convention.implementations
import team.duckie.quackquack.convention.libs

/**
 * Android 프레임워크의 Application 환경을 구성합니다.
 */
internal class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val (versionName, versionCode) = getPlaygroundVersion()

            applyPlugins(
                PluginEnum.AndroidApplication,
                PluginEnum.AndroidKotlin,
                libs.findPlugin("oss-license").get().get().pluginId,
            )

            extensions.configure<BaseAppModuleExtension> {
                configureApplication(
                    extension = this,
                )

                defaultConfig {
                    targetSdk = ApplicationConstants.targetSdk
                    this.versionName = versionName
                    this.versionCode = versionCode
                }

            }

            dependencies {
                implementations(
                    libs.findLibrary("util-oss-license").get(),
                )
            }
        }
    }
}
