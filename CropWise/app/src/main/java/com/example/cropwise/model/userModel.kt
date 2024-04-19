package com.example.cropwise.model

data class userModel(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val uid: String = "",

)

data class cropParams(
    val K: Int,
    val N: Int,
    val P: Int,
    val humidity: Int,
    val ph: Int,
    val rainfall: Int,
    val temperature: Int
)