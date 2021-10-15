package com.bnp.tiktaktoe

import org.junit.Test

class TikTakToeBoardTest {

    @Test
    fun `first move should be X player`() {
        val gameController = TikTakController()
        gameController.move(Player.O,1)
    }


}