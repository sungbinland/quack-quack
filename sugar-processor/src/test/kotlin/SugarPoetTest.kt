/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)
@file:Suppress(
    "RedundantUnitReturnType",
    "RedundantVisibilityModifier",
    "RedundantUnitExpression",
    "RedundantSuppression",
    "LongMethod",
    "HasPlatformType",
    "KDocUnresolvedReference",
)

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.spec.tempdir
import java.io.File
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.JvmTarget
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.withNotNull

class SugarPoetTest : StringSpec() {
    private val temporaryFolder = tempdir()

    init {
        "@SugarName이 없을 때는 기본 정책대로 sugar component가 생성됨" {
            val result = compile(
                kotlin(
                    "text.kt",
                    """
                    import team.duckie.quackquack.sugar.material.SugarToken
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.Modifier
    
                    @Composable
                    fun QuackText(
                        modifier: Modifier = Modifier,
                        text: String,
                        @SugarToken style: AwesomeType2,
                        singleLine: Boolean = false,
                        softWrap: Boolean = true,
                    ) {}
                    """,
                ),
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
            expectThat(findGeneratedSugarFileOrNull("text.kt")).withNotNull {
                get(File::readText).isKtEqualToWithoutPackage(
                    """
                    // This file was automatically generated by core-sugar-processor-kotlinc.
                    // Do not modify it arbitrarily.
                    // @formatter:off
                    @file:Suppress("NoConsecutiveBlankLines", "ModifierParameter", "RedundantUnitReturnType",
                        "Wrapping", "TrailingCommaOnCallSite", "NoUnitReturn", "ArgumentListWrapping", "ktlint")
                    @file:OptIn(SugarCompilerApi::class)
                    @file:GeneratedFile
    
    
                    import AwesomeType2
                    import QuackText
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.Modifier
                    import kotlin.Boolean
                    import kotlin.OptIn
                    import kotlin.String
                    import kotlin.Suppress
                    import kotlin.Unit
                    import team.duckie.quackquack.casa.`annotation`.Casa
                    import team.duckie.quackquack.sugar.material.GeneratedFile
                    import team.duckie.quackquack.sugar.material.SugarCompilerApi
                    import team.duckie.quackquack.sugar.material.SugarRefer
                    import team.duckie.quackquack.sugar.material.sugar
                    
                    /**
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackOneText(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.One,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
                    
                    /**
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackTwoText(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.Two,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
                    
                    /**
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackThreeText(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.Three,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
    
                    """,
                )
            }
        }

        """
            PREFIX_NAME + Awesome + TOKEN_NAME 조합으로 sugar component를 생성함.
            또한 KDoc을 가짐. 단, KDoc은 default section만 복사됨.
        """ {
            val result = compile(
                kotlin(
                    "text.kt",
                    """
                    import team.duckie.quackquack.sugar.material.SugarToken
                    import team.duckie.quackquack.sugar.material.SugarName
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.Modifier
                    
                    /**
                     * AWESOME! 
                     * 
                     * @param modifier 적용할 Modifier
                     * @param text 표시할 문자 
                     */
                    @SugarName(SugarName.PREFIX_NAME + "Awesome" + SugarName.TOKEN_NAME)
                    @Composable
                    fun QuackText(
                        modifier: Modifier = Modifier,
                        text: String,
                        @SugarToken style: AwesomeType2,
                        singleLine: Boolean = false,
                        softWrap: Boolean = true,
                    ) {}
                    """,
                ),
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
            expectThat(findGeneratedSugarFileOrNull("text.kt")).withNotNull {
                get(File::readText).isKtEqualToWithoutPackage(
                    """
                    // This file was automatically generated by core-sugar-processor-kotlinc.
                    // Do not modify it arbitrarily.
                    // @formatter:off
                    @file:Suppress("NoConsecutiveBlankLines", "ModifierParameter", "RedundantUnitReturnType",
                        "Wrapping", "TrailingCommaOnCallSite", "NoUnitReturn", "ArgumentListWrapping", "ktlint")
                    @file:OptIn(SugarCompilerApi::class)
                    @file:GeneratedFile
    
    
                    import AwesomeType2
                    import QuackText
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.Modifier
                    import kotlin.Boolean
                    import kotlin.OptIn
                    import kotlin.String
                    import kotlin.Suppress
                    import kotlin.Unit
                    import team.duckie.quackquack.casa.`annotation`.Casa
                    import team.duckie.quackquack.sugar.material.GeneratedFile
                    import team.duckie.quackquack.sugar.material.SugarCompilerApi
                    import team.duckie.quackquack.sugar.material.SugarRefer
                    import team.duckie.quackquack.sugar.material.sugar
                    
                    /**
                     * AWESOME!
                     *
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackAwesomeOne(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.One,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
                    
                    /**
                     * AWESOME!
                     *
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackAwesomeTwo(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.Two,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
                    
                    /**
                     * AWESOME!
                     *
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackAwesomeThree(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.Three,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
    
                    """,
                )
            }
        }

        "DEFAULT_NAME을 사용하면 기본 정책대로 sugar component가 생성됨" {
            val result = compile(
                kotlin(
                    "text.kt",
                    """
                    import team.duckie.quackquack.sugar.material.SugarToken
                    import team.duckie.quackquack.sugar.material.SugarName
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.Modifier
    
                    @SugarName(SugarName.DEFAULT_NAME)
                    @Composable
                    fun QuackText(
                        modifier: Modifier = Modifier,
                        text: String,
                        @SugarToken style: AwesomeType2,
                        singleLine: Boolean = false,
                        softWrap: Boolean = true,
                    ) {}
                    """,
                ),
            )

            expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
            expectThat(findGeneratedSugarFileOrNull("text.kt")).withNotNull {
                get(File::readText).isKtEqualToWithoutPackage(
                    """
                    // This file was automatically generated by core-sugar-processor-kotlinc.
                    // Do not modify it arbitrarily.
                    // @formatter:off
                    @file:Suppress("NoConsecutiveBlankLines", "ModifierParameter", "RedundantUnitReturnType",
                        "Wrapping", "TrailingCommaOnCallSite", "NoUnitReturn", "ArgumentListWrapping", "ktlint")
                    @file:OptIn(SugarCompilerApi::class)
                    @file:GeneratedFile
    
    
                    import AwesomeType2
                    import QuackText
                    import androidx.compose.runtime.Composable
                    import androidx.compose.ui.Modifier
                    import kotlin.Boolean
                    import kotlin.OptIn
                    import kotlin.String
                    import kotlin.Suppress
                    import kotlin.Unit
                    import team.duckie.quackquack.casa.`annotation`.Casa
                    import team.duckie.quackquack.sugar.material.GeneratedFile
                    import team.duckie.quackquack.sugar.material.SugarCompilerApi
                    import team.duckie.quackquack.sugar.material.SugarRefer
                    import team.duckie.quackquack.sugar.material.sugar
                    
                    /**
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackOneText(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.One,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
                    
                    /**
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackTwoText(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.Two,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
                    
                    /**
                     * This document was auto-generated. Please see [QuackText] for details.
                     */
                    @Composable
                    @Casa
                    @SugarRefer("QuackText")
                    public fun QuackThreeText(
                      modifier: Modifier = sugar(),
                      text: String,
                      singleLine: Boolean = sugar(),
                      softWrap: Boolean = sugar(),
                    ): Unit {
                      QuackText(
                        modifier = modifier,
                        text = text,
                        style = AwesomeType2.Three,
                        singleLine = singleLine,
                        softWrap = softWrap,
                      )
                    }
    
                    """,
                )
            }
        }
    }

    private fun compile(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return prepareCompilation(*sourceFiles).compile()
    }

    private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            workingDir = temporaryFolder
            sources = sourceFiles.asList() + stubs
            jvmTarget = JvmTarget.JVM_17.toString()
            supportsK2 = false
            pluginOptions = listOf(
                PluginOption(
                    pluginId = PluginId,
                    optionName = OPTION_SUGAR_PATH.optionName,
                    optionValue = temporaryFolder.path,
                ),
                PluginOption(
                    pluginId = PluginId,
                    optionName = OPTION_POET.optionName,
                    optionValue = "true",
                ),
            )
            verbose = false
            inheritClassPath = true
            compilerPluginRegistrars = listOf(SugarComponentRegistrar.asPluginRegistrar())
            commandLineProcessors = listOf(SugarCommandLineProcessor())
            useK2 = false
        }
    }

    private fun findGeneratedSugarFileOrNull(@Suppress("SameParameterValue") fileName: String): File? {
        return temporaryFolder
            .walkTopDown()
            .find { file ->
                file.name == fileName
            }
    }
}
