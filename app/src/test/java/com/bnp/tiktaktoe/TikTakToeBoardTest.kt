package com.bnp.tiktaktoe

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class TikTakToeBoardTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `when first move is Player O game should throws exception`() {
        val gameController = TikTakController()
        Assert.assertThrows(PlayerXShouldStartGameException::class.java) {
            gameController.move(Player.O, 1)
        }
    }

    @Test
    fun `when first move is Player X game controller should run normally`() {
        val gameController = TikTakController()
        try {
            gameController.move(Player.X, 1)
            assert(true)
        } catch (e: Exception) {
            assert(false)
        }
    }

    @Test

    fun `when play move is successful board should be updated`() = runBlocking {
        val gameController = TikTakController()
        gameController.move(Player.X, 1)
        gameController.state.test {
            val item = awaitItem()
            assertEquals(mutableSetOf(1), item.board[Player.X])
        }
    }

    @Test
    fun `when Player X moves turn should be changes  to Player Y `() = runBlocking {
        val gameController = TikTakController()
        gameController.move(Player.X, 1)

        gameController.state.test {
            val item = awaitItem()
            assertEquals(Player.O, item.currentTurn)
        }
    }
    @Test
    fun `when Player Y moves turn should be changes  to Player X`() = runBlocking {
        val gameController = TikTakController()
        gameController.move(Player.X, 1)
        gameController.move(Player.O, 2)

        gameController.state.test {
            val item = awaitItem()
            assertEquals(Player.X, item.currentTurn)
        }
    }


}