package pe.edu.upeu.ExamenUnidad1.model

data class ResultResponse(
    var codigo: String,
    var mensaje: String,
    var data: ArrayList<Result>
)