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