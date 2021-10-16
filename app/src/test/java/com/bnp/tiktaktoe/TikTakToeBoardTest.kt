package com.bnp.tiktaktoe

import app.cash.turbine.test
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalStdlibApi
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
    fun `when play move is successful board should be updated`() = runBlocking {
        val gameController = TikTakController()
        gameController.move(1)
        gameController.state.test {
            val item = awaitItem()
            assertEquals(Player.X, item.board[1])
        }
    }

    @Test
    fun `when Player X moves turn should be changes  to Player Y `() = runBlocking {
        val gameController = TikTakController()
        gameController.move(1)

        gameController.state.test {
            val item = awaitItem()
            assertEquals(Player.O, item.currentTurn)
        }
    }

    @Test
    fun `when Player Y moves turn should be changes  to Player X`() = runBlocking {
        val gameController = TikTakController()
        gameController.move(1)
        gameController.move(2)

        gameController.state.test {
            val item = awaitItem()
            assertEquals(Player.X, item.currentTurn)
        }
    }

    @Test
    fun `when player choose the chosen position state should has exception`() = runBlocking {
        val gameController = TikTakController()
        gameController.move(1)
        gameController.move(1)

        gameController.state.test {
            val item = awaitItem()
            assertEquals(Player.O, item.currentTurn)
            assertEquals(
                PositionHasAlreadyChosenException, item.exception
            )
        }
    }


    @Test
    fun `when X Player choose fist column state should be Win( Player-X) `() = runBlocking {
        val gameController = TikTakController()
        gameController.move(0)//x
        gameController.move(4)//0
        gameController.move(3)//x
        gameController.move(2)//0
        gameController.move(6)//x
        assertEquals(Result.Win(player = Player.X), gameController.state.value.result)
    }

    @Test
    fun `when Players choose 9 cells and not of them is winner it should be draw `() = runBlocking {
        val gameController = TikTakController()
        gameController.move(0)//x
        gameController.move(1)//0
        gameController.move(2)//x
        gameController.move(5)//0
        gameController.move(3)//x
        gameController.move(8)//0
        gameController.move(4)//x
        gameController.move(6)//0
        gameController.move(7)//x
        assertEquals(Result.Draw, gameController.state.value.result)
    }

    @Test
    fun `when  restart is called on controller state should return to initial state`() =
        runBlocking {
            val gameController = TikTakController()

            gameController.move(0)//x
            gameController.move(1)//0
            gameController.move(2)//x
            gameController.move(5)//0
            gameController.move(3)//x
            gameController.move(8)//0
            gameController.move(4)//x
            gameController.move(6)//0
            gameController.move(7)//x

            gameController.restart()

            assertEquals(
                GameState(
                    Result.NotFinished,
                    mutableMapOf(),
                    Player.X
                ), gameController.state.value
            )
        }
}