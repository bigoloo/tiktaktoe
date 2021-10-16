package com.bnp.tiktaktoe

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class GameActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `WhenComponentIsInitializeBoardShouldHas9Cells`() {

        composeTestRule.setContent {
            GameBoard(
                mutableStateOf(
                    GameState(
                        Result.NotFinished,
                        board = mutableMapOf<Int, Player>(),
                        Player.X,
                        exception = null
                    )
                )
            ) {

            }
        }

        for (i in 0 until 9) {
            composeTestRule.onNodeWithTag(i.toString()).assertExists()
        }
    }

    @Test
    fun `WhenCellIsClickedCallbackShouldBeCalled`() {

        val mockCallback = mockk<(position: Int) -> Unit>()
        every { mockCallback(any()) } returns Unit
        composeTestRule.setContent {
            GameBoard(
                mutableStateOf(
                    GameState(
                        Result.NotFinished,
                        board = mutableMapOf<Int, Player>(),
                        Player.X,
                        exception = null
                    )
                ), mockCallback
            )
        }

        for (i in 0 until 9) {
            composeTestRule.onNodeWithTag("$i").performClick()
            verify(exactly = 1) {
                mockCallback(i)
            }
        }
    }

    @Test
    fun `WhenRestartButtonClickedCallbackShouldBeCalled`() {
        val mockCallback = mockk<() -> Unit>()
        every { mockCallback() } returns Unit
        composeTestRule.setContent {
            InfoSection(
                mutableStateOf(
                    GameState(
                        Result.NotFinished,
                        board = mutableMapOf<Int, Player>(),
                        Player.X,
                        exception = null
                    )
                ), mockCallback
            )
        }
        composeTestRule.onNodeWithTag("restart_button").performClick()
        verify(exactly = 1) {
            mockCallback()
        }
    }
}