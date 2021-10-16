package com.bnp.tiktaktoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TikTakViewModel(private val tikTokController: TikTakController) : ViewModel() {


    val gameState = tikTokController.state
    fun move(position: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            tikTokController.move(position)
        }
    }

    fun restartGame() {
        tikTokController.restart()
    }
}