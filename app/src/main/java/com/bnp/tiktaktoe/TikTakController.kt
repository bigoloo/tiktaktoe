package com.bnp.tiktaktoe

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TikTakController {

    private val _result = MutableStateFlow<Result>(Result.NotFinished)
    val result: StateFlow<Result> = _result

   private  val moves = mutableMapOf<Player, List<Int>>()

    fun move(player: Player, position: Int) {
        if (moves.isEmpty() && player != Player.X) throw PlayerXShouldStartGameException
    }
}

val PlayerXShouldStartGameException = Exception("player X should Start The game")