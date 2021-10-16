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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
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
                Column {
                    val state = gameViewModel.gameController.state.collectAsState()
                    GameBoard(state) {
                        gameViewModel.gameController.move(it)
                    }
                    InfoSection(state) {
                        gameViewModel.gameController.restart()
                    }
                }
            }
        }
    }

}


@Composable
fun InfoSection(state: State<GameState>, restartCallBack: () -> Unit) {
    Text(text = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Blue)) {
            append("Result: ")
        }
        withStyle(SpanStyle(color = Color.Black)) {
            append(
                when (val result = state.value.result) {
                    Result.Draw -> "Game is Draw "
                    Result.NotFinished -> "Game is Running"
                    is Result.Win -> "WINNER IS :" + when (result.player) {
                        Player.O -> "O"
                        Player.X -> "X"
                    }
                }
            )
        }
    })
    Text(text = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Blue)) {
            append("current Turn is : ")
        }
        withStyle(SpanStyle(color = Color.Black)) {
            append(
                when (state.value.currentTurn) {
                    Player.O -> "Player O"
                    Player.X -> "Player X"
                }
            )
        }
    })
    Text(text = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Blue)) {
            append("Exception : ")
        }
        withStyle(SpanStyle(color = Color.Black)) {
            append(
                state.value.exception?.message ?: "----"
            )
        }
    })
    Button(modifier = Modifier.testTag("restart_button"), onClick = { restartCallBack() }) {
        Text(text = "Restart The Game")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameBoard(state: State<GameState>, callback: (position: Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.wrapContentSize(),
        cells = GridCells.Fixed(3),
    ) {
        items(9) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .testTag("$it")
                    .height(40.dp)
                    .width(40.dp)

                    .clickable {
                        callback(it)
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