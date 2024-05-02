package pe.edu.upeu.ExamenUnidad1.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upeu.ExamenUnidad1.ViewModel.ResultViewModel
import pe.edu.upeu.ExamenUnidad1.model.Result
import pe.edu.upeu.ExamenUnidad1.ui.presentation.Components.CardResult

@Composable
fun Board(resultsList: ArrayList<Result>, viewModel: ResultViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(resultsList) { result ->
                        CardResult(
                            resultC = result,
                            deleteResult = {
                                viewModel.deleteResult(result.id_resultado)
                            }
                        )
                    }
                }
            }
        }
    }
}