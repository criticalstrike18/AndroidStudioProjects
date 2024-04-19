package com.example.cropwise.model

import com.example.cropwise.model.Current as Current1
import com.example.cropwise.model.Location as Location1

data class Locationdata(
    val latitude: Double,
    val longitude: Double
)

data class Extraparams(
    val current: Current1,
    val location: Location1

)

data class SendPricePredict(
    val state: Int,
    val commodity: Int,
    val month: Int
)

data class recivingPricePredict(
    val predicted_price: Double
)