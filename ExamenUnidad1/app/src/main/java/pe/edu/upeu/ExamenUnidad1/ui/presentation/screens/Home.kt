package pe.edu.upeu.ExamenUnidad1.ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pe.edu.upeu.ExamenUnidad1.ui.navigation.MyAppRoute

@Composable
fun Home(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navController.navigate(MyAppRoute.GAME)
            },colors = ButtonDefaults.buttonColors(Color(0xFF509BF8)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(160.dp)
                .height(50.dp)
        ) {
            Text(text = "PLAY")
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    val navController = rememberNavController()
    Home(navController = navController)
}