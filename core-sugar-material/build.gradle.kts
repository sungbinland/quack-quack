/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

plugins {
    `buildlogic-jvm-kotlin`
    `buildlogic-kotlin-explicitapi`
    `buildlogic-quack-mavenpublishing`
}

quack {
    type = QuackArtifactType.CoreSugarMaterial.forceInternal()
}
