package com.example.cropwise.data

import com.example.cropwise.model.Extraparams
import com.example.cropwise.model.SendPricePredict
import com.example.cropwise.model.cropParams
import com.example.cropwise.model.recivingCropRecom
import com.example.cropwise.model.recivingPricePredict
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.text.DecimalFormat

interface ApiInterface {
    @POST("/")
    suspend fun getCropRecom(@Body requestData: cropParams):Response<recivingCropRecom>

    @POST("/predict")
    suspend fun getPricePredict(@Body requestData: SendPricePredict):Response<recivingPricePredict>

    @GET("/v1/current.json")
    suspend fun getExtraparams(
        @Query("key")  key: String,
        @Query("q")  location: String
    ):Response<Extraparams>
}