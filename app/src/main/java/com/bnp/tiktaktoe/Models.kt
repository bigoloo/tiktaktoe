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

val PositionHasAlreadyChosenException = Exception("Position Has Already Chosen!")
val GameIsOverException = Exception("Game is over")

data class GameState(
    val result: Result,
    val board: MutableMap<Int,Player>,
    val currentTurn: Player,
    val exception: Exception? = null
)