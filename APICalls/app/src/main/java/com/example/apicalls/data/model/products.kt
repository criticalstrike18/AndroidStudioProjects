package com.example.apicalls.data.model

data class products(
    val limit: Int,
    val products: List<ProductX>,
    val skip: Int,
    val total: Int
)