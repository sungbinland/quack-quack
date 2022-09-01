/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [text.kt] created by Ji Sungbin on 22. 8. 14. 오후 11:49
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:OptIn(ExperimentalAnimationApi::class)

package team.duckie.quackquack.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import team.duckie.quackquack.ui.animation.quackTween
import team.duckie.quackquack.ui.typography.QuackTextStyle
import team.duckie.quackquack.ui.typography.animateQuackTextStyleAsState

/**
 * 주어진 조건에 따라 텍스트를 표시합니다. 꽥꽥에서 텍스트를 표시하는데
 * 사용되는 최하위 컴포넌트 입니다.
 *
 * **애니메이션 가능한 모든 요소들에는 자동으로
 * 애니메이션이 적용됩니다.** 현재 애니메이션이
 * 적용되는 요소들은 다음과 같습니다.
 *
 * [text], [style]
 *
 * @param modifier 이 컴포저블에서 사용할 [Modifier]
 * @param text 표시할 텍스트 내용
 * @param style 텍스트를 그릴 [TextStyle] 의 QuackQuack 버전
 */
@Composable
@NonRestartableComposable
internal fun QuackText(
    modifier: Modifier = Modifier,
    text: String,
    style: QuackTextStyle,
) {
    val styleAnimationState by animateQuackTextStyleAsState(
        targetValue = style,
    )

    AnimatedContent(
        modifier = modifier,
        targetState = text,
        transitionSpec = {
            fadeIn(
                animationSpec = quackTween(),
            ) with fadeOut(
                animationSpec = quackTween(),
            )
        },
    ) {
        BasicText(
            text = text,
            style = styleAnimationState.asComposeStyle(),
        )
    }
}
