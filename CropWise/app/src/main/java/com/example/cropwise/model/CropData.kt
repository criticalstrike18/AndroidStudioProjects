package com.example.cropwise.model

data class CropData(
    val name:String,
    val imageResId: Int,
    val headResId: Int,
    val descriptionResId: Int,
    val fertReqId: Int,
    val beforePlanting: List<String>,
    val afterPlanting: List<String>
)

