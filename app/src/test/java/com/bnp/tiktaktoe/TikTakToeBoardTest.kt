package com.bnp.tiktaktoe

import org.junit.Assert
import org.junit.Test

class TikTakToeBoardTest {

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


}