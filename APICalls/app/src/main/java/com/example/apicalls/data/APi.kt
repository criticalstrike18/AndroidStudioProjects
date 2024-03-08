package com.example.apicalls.data


import com.example.apicalls.data.model.products
import retrofit2.http.GET

interface APi {
    @GET("products/{type}")
    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("api_key") apiKey: String
    ) :products
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }


}