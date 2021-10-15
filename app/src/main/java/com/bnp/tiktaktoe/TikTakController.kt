package com.bnp.tiktaktoe

import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalStdlibApi
class TikTakController() {
    private val playersMove: MutableMap<Player, IntArray> = mutableMapOf()
    private val initialState = GameState(
        Result.NotFinished,
        mutableMapOf(),
        Player.X
    )
    val state =
        MutableStateFlow<GameState>(
            initialState
        )


    init {

        initPlayerMoves(playersMove)

    }


    private fun initPlayerMoves(playersMove: MutableMap<Player, IntArray>) {
        playersMove.apply {
            put(Player.X, IntArray(8) { 0 })
            put(Player.O, IntArray(8) { 0 })

        }
    }

    fun move(position: Int) {
        val currentState = state.value
        if (currentState.result == Result.Draw) {
            state.value = currentState.copy(exception = GameIsOverException)
            return
        }
        state.value = currentState.copy(exception = null)

        currentState.board[position]?.let {
            state.value = currentState.copy(exception = PositionHasAlreadyChosenException)
        } ?: run {
            currentState.board[position] = currentState.currentTurn
            val row = position.div(3)
            val column = position.rem(3)
            playersMove[currentState.currentTurn]!![row] =
                playersMove[currentState.currentTurn]!![row] + 1
            playersMove[currentState.currentTurn]!![column + 3] =
                playersMove[currentState.currentTurn]!![column + 3] + 1
            if (position == 0 || position == 8) playersMove[currentState.currentTurn]!![6] =
                playersMove[currentState.currentTurn]!![6] + 1
            if (position == 2 || position == 6) playersMove[currentState.currentTurn]!![7] =
                playersMove[currentState.currentTurn]!![7] + 1
            if (position == 4) {
                playersMove[currentState.currentTurn]!![6] =
                    playersMove[currentState.currentTurn]!![6] + 1
                playersMove[currentState.currentTurn]!![7] =
                    playersMove[currentState.currentTurn]!![7] + 1
            }
            when {
                playersMove[Player.X]!!.contains(3) -> state.value =
                    currentState.copy(
                        result = Result.Win(
                            Player.X
                        )
                    )
                playersMove[Player.O]!!.contains(3) -> state.value =
                    currentState.copy(result = Result.Win(Player.O))

                else -> {
                    if (currentState.board.size == 9) {
                        state.value = currentState.copy(result = Result.Draw)
                    }
                }
            }
            switchTurn()
        }
    }


    private fun switchTurn() {
        when (state.value.currentTurn) {
            Player.O -> state.value = state.value.copy(currentTurn = Player.X)
            Player.X -> state.value = state.value.copy(currentTurn = Player.O)
        }
    }

    fun restart() {
        state.value =
            initialState
        playersMove.clear()
        initPlayerMoves(playersMove)
    }
}
