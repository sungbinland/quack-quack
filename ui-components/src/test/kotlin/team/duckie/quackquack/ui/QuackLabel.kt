/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.ui.unit.dp
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class  QuackLabel {

    @get:Rule
    val paparazzi = buildPaparazzi()
    @Test
    fun QuackSimpleLabel(
        @TestParameter("완료","거래중") text: String,
        @TestParameter("true","false") active: Boolean,
    ) {
        paparazzi.boxSnapshot(
            name = "[text:$text-active:$active]",
        ) {
            team.duckie.quackquack.ui.component.QuackSimpleLabel(
                text = text,
                active = active,
            )
        }
    }
}
