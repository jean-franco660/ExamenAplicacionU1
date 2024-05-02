package pe.edu.upeu.ExamenUnidad1.ViewModel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import pe.edu.upeu.ExamenUnidad1.di.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.edu.upeu.ExamenUnidad1.model.Result

class ResultViewModel: ViewModel() {

    var resultsListApi: ArrayList<Result> by mutableStateOf(arrayListOf())

    fun getResults() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.getResults()
            withContext(Dispatchers.Main) {
                if (response.body()!!.codigo == "200") {
                    resultsListApi = response.body()!!.data
                }
            }
        }
    }

    fun addResult(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.addResult(result)
            withContext(Dispatchers.Main) {
                if(response.body()!!.codigo == "200") {
                    getResults()
                }
            }
        }
    }
    fun deleteResult(idResult: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.deleteResult(idResult)
            withContext(Dispatchers.Main) {
                if(response.body()!!.codigo == "200") {
                    getResults()
                }
            }
        }
    }
}
