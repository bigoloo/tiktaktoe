package com.bnp.tiktaktoe

import kotlinx.coroutines.flow.MutableStateFlow

class TikTakController {

    val state =
        MutableStateFlow<GameState>(GameState(Result.NotFinished, mutableMapOf(), Player.X))


    fun move(player: Player, position: Int) {
        if (state.value.board.isEmpty() && player != Player.X) throw PlayerXShouldStartGameException

        if (!state.value.board.containsKey(player)) {
            state.value.board[player] = mutableSetOf<Int>(position)
        } else {
            state.value.board[player]!!.add(position)
        }

        switchTurn()
    }

    private fun switchTurn() {
        when (state.value.currentTurn) {
            Player.O -> state.value = state.value.copy(currentTurn = Player.X)
            Player.X ->  state.value = state.value.copy(currentTurn = Player.O)
        }
    }
}
