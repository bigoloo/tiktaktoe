package com.bnp.tiktaktoe

sealed class Player {
    object X : Player()
    object O : Player()
}

sealed class Result {
    data class Win(val player: Player) : Result()
    object Draw : Result()
    object NotFinished : Result()
}


val PlayerXShouldStartGameException = Exception("player X should Start The game")


data class GameState(
    val result: Result,
    val board: MutableMap<Player, MutableSet<Int>>,
    val currentTurn: Player
)