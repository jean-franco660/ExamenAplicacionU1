package pe.edu.upeu.ExamenUnidad1.ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pe.edu.upeu.ExamenUnidad1.ViewModel.ResultViewModel
import pe.edu.upeu.ExamenUnidad1.model.Result
import pe.edu.upeu.ExamenUnidad1.ui.navigation.MyAppRoute
import java.util.UUID

enum class Player {
    X, O
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(navController: NavHostController, viewModel:ResultViewModel) {

    //Variables
    var playerOneText by remember { mutableStateOf(TextFieldValue("")) }
    var playerTwoText by remember { mutableStateOf(TextFieldValue("")) }
    //Tabla del juego
    var board by remember { mutableStateOf(listOf<Player?>(null, null, null, null, null, null, null, null, null)) }
    //Jugador actual
    var currentPlayer by remember { mutableStateOf(Player.X) }
    //Ganador
    var winner by remember { mutableStateOf<Player?>(null) }
    //Juego finalizado
    var gameFinished by remember { mutableStateOf(false) }
    // Variable para rastrear si el juego ha comenzado
    var gameStarted by remember { mutableStateOf(false) }

    // Variables para llevar un registro del puntaje
    var player1Score by remember { mutableStateOf(0) }
    var player2Score by remember { mutableStateOf(0) }





    //Code
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Tic Toc Toe",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.1.sp,
                    lineHeight = 32.sp,
                    color = Color(0xFFB8B8B8)
                )
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 15.dp)
        ){
            OutlinedTextField(
                value = playerOneText.text,
                label = { Text(text = "PlayerOne") },
                onValueChange = {
                    playerOneText = TextFieldValue(text = it)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = playerTwoText.text,
                label = { Text(text = "PlayerOne") },
                onValueChange = {
                    playerTwoText = TextFieldValue(text = it)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(1.dp))
        Row(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val idResultRandom = UUID.randomUUID().toString()
                viewModel.addResult(
                    Result(
                        id_resultado = "",
                        nombre_partida = idResultRandom,
                        nombre_jugador1 = playerOneText.text,
                        nombre_jugador2 = playerTwoText.text,
                        ganador = "null",
                        punto = "null",
                        estado = "null"
                    )
                )

                gameFinished = false
                player1Score = 0
                player2Score = 0
                gameStarted = true
            },enabled = !gameStarted && playerOneText.text.isNotBlank() && playerTwoText.text.isNotBlank(),
                colors = ButtonDefaults.buttonColors(Color(0xFF509BF8)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
            ) {
                Text(text = "Start")
            }
            Button(onClick = {
                playerOneText= TextFieldValue()
                playerTwoText = TextFieldValue()
                gameStarted = false
            },colors = ButtonDefaults.buttonColors(Color(0xFF509BF8)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
            ) {
                Text(text = "Cancel")
            }
        }
        //Falta Editar
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .background(Color.White)
            .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //-------
            if (gameStarted && !gameFinished) {
                GridBoard(board, currentPlayer, winner) { row, col ->
                    if (winner == null && board[row * 3 + col] == null) {
                        board = board.toMutableList().also {
                            it[row * 3 + col] = currentPlayer
                        }
                        winner = checkForWinner(board)

                        if (winner == null) {
                            // Si no hay ganador, cambiar al siguiente jugador
                            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
                        }

                        if (board.all { it != null } || winner != null) {
                            gameFinished = true

                            // Actualizar el puntaje si hay un ganador
                            if (winner == Player.X) {
                                player1Score++
                            } else if (winner == Player.O) {
                                player2Score++
                            }
                        }
                    }
                }
            }

        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(
                    color = Color(0xFFF8F9FD),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically

            ){
                Row(modifier = Modifier
                    .background(
                        color = Color(0xFFEBEEFD),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .height(32.dp)
                    .width(32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Text(text = "T:")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${if (currentPlayer == Player.X) playerOneText.text else playerTwoText.text}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.1.sp,
                        lineHeight = 32.sp,
                        color = Color(0xFF000000)
                    )
                )
            }
            Row(modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(
                    color = Color(0xFFF8F9FD),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically

            ){
                Row(modifier = Modifier
                    .background(
                        color = Color(0xFFEBEEFD),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .height(32.dp)
                    .width(32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Text(text = "W:")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = when {
                        winner != null -> "${if (winner == Player.X) playerOneText.text else playerTwoText.text}"
                        gameFinished -> "It's a draw!"
                        else -> ""
                    },
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.1.sp,
                        lineHeight = 32.sp,
                        color = Color(0xFF000000)
                    )
                )
            }
        }
//-----------------------------------------------------

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(
                    color = Color(0xFFDCE2F8),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Row(modifier = Modifier
                    .background(
                        color = Color(0xFF6482E2),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .height(32.dp)
                    .width(32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Text(text = " ${player1Score ?: "N/A"}", style = TextStyle(
                        color = Color(0xFFFFFFFF)
                    ))
                }
                Row(modifier = Modifier
                    .background(Color(0xFFDCE2F8))
                    .height(32.dp)
                    .width(105.dp),
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Text(text = "${playerOneText.text}")
                }
            }
            Box(modifier = Modifier
                .background(Color.White)
                .padding(10.dp)){
                Text(text = "VS")
            }
            Row(modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(
                    color = Color(0xFFDCE2F8),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){

                Row(modifier = Modifier
                    .background(Color(0xFFDCE2F8))
                    .height(32.dp)
                    .width(105.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End

                ){
                    Text(text = "${playerTwoText.text}")
                }
                Row(modifier = Modifier
                    .background(
                        color = Color(0xFF6482E2),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .height(32.dp)
                    .width(32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ){
                    Text(text = "$player2Score", style = TextStyle(
                        color = Color(0xFFFFFFFF)
                    )
                    )
                }
            }

        }

        Row (modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White)
            .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {
                navController.navigate(MyAppRoute.BOARD)
            },colors = ButtonDefaults.buttonColors(Color(0xFF509BF8)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
            ) {
                Text(text = "Score table")
            }
            Button(onClick = {
                board = List(9) { null }
                currentPlayer = Player.X
                winner = null
                gameFinished = false

            },colors = ButtonDefaults.buttonColors(Color(0xFF509BF8)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
            ) {
                Text(text = "Play again")
            }
        }
    }
}






@Composable
fun GridBoard(
    board: List<Player?>,
    currentPlayer: Player,
    winner: Player?,
    onTileClick: (row: Int, col: Int) -> Unit
) {
    Column {
        for (row in 0 until 3) {
            Row(modifier = Modifier
                .background(Color.White)
            ){
                for (col in 0 until 3) {
                    val player = board[row * 3 + col]
                    val backgroundColor = if (winner != null) {
                        Color.Gray
                    } else {
                        if (player == Player.X) {
                            Color.Blue
                        } else if (player == Player.O) {
                            Color.Red
                        } else {
                            Color.White
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(105.dp)
                            .background(Color(0xFF2956ED))
                            .clickable {
                                if (player == null && winner == null) {
                                    onTileClick(row, col)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = player?.name ?: "",
                            fontSize = 36.sp,
                            color = Color.White
                        )
                    }
                    if (col < 2) {
                        Spacer(modifier = Modifier
                            .width(4.dp)
                            .background(Color.White))
                    }
                }
            }
            if (row < 2) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

fun checkForWinner(board: List<Player?>): Player? {
    val winningCombos = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    for (combination in winningCombos) {
        val (a, b, c) = combination
        if (board[a] != null && board[a] == board[b] && board[a] == board[c]) {
            return board[a]
        }
    }

    return null
}


@Preview
@Composable
fun PrebiwGame(){
    val navController = rememberNavController()
    Game(navController = navController, viewModel = ResultViewModel())
}