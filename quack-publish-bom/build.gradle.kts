/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import team.duckie.quackquack.convention.QuackArtifactType

plugins {
    id(libs.plugins.gradle.maven.publish.base.get().pluginId)
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidQuackPublish)
}

group = "team.duckie.quack"
version = libs.versions.quack.bom.get()

publishing {
    publications.withType<MavenPublication> {
        artifactId = "quack-bom"
    }
}

android {
    namespace = "team.duckie.quackquack.publish.bom"
}

quackArtifactPublish {
    type = QuackArtifactType.Bom
}

dependencies {
    constraints {
        apis(
            projects.uiComponents,
            projects.lintCorePublish,
            projects.lintQuackPublish,
            projects.lintComposePublish,
        )
    }
}
