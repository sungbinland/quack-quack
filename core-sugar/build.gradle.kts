/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

plugins {
    `buildlogic-android-library`
    `buildlogic-android-compose`
}

android {
    namespace = "team.duckie.quackquack.sugar"
}

dependencies {
    api(projects.core)
}
