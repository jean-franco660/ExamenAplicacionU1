package pe.edu.upeu.ExamenUnidad1.di

import com.google.gson.GsonBuilder
import com.kotlin.tres_en_raya.Service.WebService
import com.kotlin.tres_en_raya.constants.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }
}