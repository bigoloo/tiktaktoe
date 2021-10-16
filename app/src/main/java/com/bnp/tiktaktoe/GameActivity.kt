package com.bnp.tiktaktoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

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
            append("Status: ")
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
            append("Current Turn is : ")
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
    state.value.exception?.let {
        Text(text = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Blue)) {
                append("Exception : ")
            }
            withStyle(SpanStyle(color = Color.Black)) {
                append(
                    it.message!!
                )
            }
        })
    }

    Button(
        modifier = Modifier
            .testTag("restart_button")
            .padding(10.dp),
        onClick = { restartCallBack() }) {
        Text(text = "Restart The Game")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameBoard(state: State<GameState>, callback: (position: Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(30.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = colorResource(id = R.color.board_background))
            .border(
                8.dp, color = Color.Black,
            ),

        cells = GridCells.Fixed(3),
    ) {
        items(9) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .testTag("$it")
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        callback(it)
                    }
                    .border(2.dp, color = Color.Black)
            ) {

                when (state.value.board[it]) {
                    Player.O -> {
                        Image(
                            modifier = Modifier
                                .width(30.dp)
                                .width(30.dp),
                            painter = painterResource(
                                id = R.drawable.ic_o_player,
                            ), contentDescription = "O player"

                        )
                    }
                    Player.X -> Image(
                        modifier = Modifier
                            .width(30.dp)
                            .width(30.dp),
                        painter = painterResource(
                            id = R.drawable.ic_x_player,
                        ), contentDescription = "x player"

                    )
                    else -> {

                    }
                }


            }
        }
    }
}