package com.bnp.tiktaktoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bnp.tiktaktoe.ui.theme.TTTTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalAnimationApi
@ExperimentalFoundationApi
class GameActivity : ComponentActivity() {
    private val gameViewModel by viewModel<TikTakViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TTTTheme {
                // A surface container using the 'background' color from the theme
                Column() {
                    val state = gameViewModel.gameController.state.collectAsState()
                    GameBoard(state)

                    Text(text = "state:+${state.value.result}")
                    Text(text = "turn:+${state.value.currentTurn}")
                    Text(text = "exception:+${state.value.exception}")
                }
            }
        }
    }

    @Composable
    private fun GameBoard(state: State<GameState>) {
        LazyVerticalGrid(
            modifier = Modifier.wrapContentSize(),
            cells = GridCells.Fixed(3),
        ) {
            items(9) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)

                        .clickable {
                            gameViewModel.gameController.move(it)
                        }
                        .border(3.dp, color = Color.Cyan)
                ) {
                    Text(
                        text = when (state.value.board[it]) {
                            Player.O -> "O"
                            Player.X -> "X"
                            null -> ""
                        }
                    )

                }
            }
        }
    }
}