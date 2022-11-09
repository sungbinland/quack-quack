/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

plugins {
    id(ConventionEnum.JvmLibrary)
    id(ConventionEnum.JvmJUnit4)
    id(ConventionEnum.JvmDokka)
}

dependencies {
    implementations(
        projects.lintCustomRuleAnnotation,
        libs.ksp.api,
    )
}
