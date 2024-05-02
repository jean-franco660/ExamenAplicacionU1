package pe.edu.upeu.ExamenUnidad1.ui.presentation.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pe.edu.upeu.ExamenUnidad1.model.Result

@Composable
fun CardResult(
    resultC: Result,
    deleteResult: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "Game: ${resultC.nombre_partida}"
            )
            Text(
                text = "PlayerOne: ${resultC.nombre_jugador1}"
            )
            Text(
                text = "PlayerTwo: ${resultC.nombre_jugador2}"
            )
        }

        Button(onClick = {
            deleteResult(resultC.id_resultado)

        },colors = ButtonDefaults.buttonColors(Color(0xFF509BF8)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Delete")
        }
    }
}