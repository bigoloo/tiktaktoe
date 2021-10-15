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
val PositionHasAlreadyChosenException = Exception("Position Has Already Chosen!")

data class GameState(
    val result: Result,
    val board: MutableList<Player?>,
    val currentTurn: Player,
    val exception: Exception? = null
) {

}