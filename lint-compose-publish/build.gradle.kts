/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

import team.duckie.quackquack.convention.QuackArtifactType

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidQuackPublish)
    id(ConventionEnum.JvmKover)
}

android {
    namespace = "team.duckie.quackquack.lint.compose.publish"
}

dependencies {
    lintPublish(
        project(
            path = ":lint-compose",
            configuration = "default",
            // https://github.com/dialogflow/dialogflow-android-client/issues/57#issuecomment-341329755
        )
    )
}

quackArtifactPublish {
    type = QuackArtifactType.LintCompose
}
