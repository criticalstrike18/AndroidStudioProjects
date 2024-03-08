package com.example.apicalls.data

import com.example.apicalls.data.model.products
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductsList(): Flow<Result<List<products>>>
}