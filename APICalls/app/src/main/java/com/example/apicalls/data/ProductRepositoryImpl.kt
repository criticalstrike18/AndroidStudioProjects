package com.example.apicalls.data

import android.net.http.HttpException
import com.example.apicalls.data.model.products
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class ProductRepositoryImpl (
    private val api : APi
): ProductRepository {
    override suspend fun getProductsList(): Flow<Result<List<products>>> {
        return flow {
            val productsFormAPi = try {

            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading products"))
                return@flow
            }
            catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error loading prodcuts"))
            }
        }
    }
}
