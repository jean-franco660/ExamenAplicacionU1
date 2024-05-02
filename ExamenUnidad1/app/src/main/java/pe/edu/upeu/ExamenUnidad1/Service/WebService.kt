package com.kotlin.tres_en_raya.Service

import com.kotlin.tres_en_raya.model.Result
import com.kotlin.tres_en_raya.model.ResultResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface WebService {
    @GET("/result")
    suspend fun getResults(): Response<ResultResponse>

    @GET("/result/{id}")
    suspend fun getResult(
        @Path("id") id_resultado: String
    ): Response<ResultResponse>

    @POST("/result")
    suspend fun addResult(
        @Body resultado: Result
    ): Response<ResultResponse>

    @PUT("/result/{id}")
    suspend fun updateResult(
        @Path("id") id_resultado: String,
        @Body resultado: Result
    ): Response<ResultResponse>

    @DELETE("/result/{id}")
    suspend fun deleteResult(
        @Path("id") id_resultado: String
    ): Response<ResultResponse>
}