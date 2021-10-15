package com.bnp.tiktaktoe

import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalStdlibApi
class TikTakController {

    val state =
        MutableStateFlow<GameState>(
            GameState(
                Result.NotFinished,
                mutableListOf<Player?>().apply {
                    repeat((0 until 9).count()) { add(null) }
                },
                Player.X
            )
        )
    val xMoves = IntArray(8) { 0 }
    val oMoves = IntArray(8) { 0 }

    fun move(player: Player, position: Int) {
        val currentState = state.value

        state.value = currentState.copy(exception = null)
        /*if (currentState.board.filterNotNull().size == 9) {
            state.value = currentState.copy(exception = PlayerXShouldStartGameException)
        }*/

        currentState.board.getOrNull(position)?.let {
            state.value = currentState.copy(exception = PositionHasAlreadyChosenException)
        } ?: run {
            currentState.board.add(position, player)
            switchTurn()
        }


    }

    private fun switchTurn() {
        when (state.value.currentTurn) {
            Player.O -> state.value = state.value.copy(currentTurn = Player.X)
            Player.X -> state.value = state.value.copy(currentTurn = Player.O)
        }
    }
}
