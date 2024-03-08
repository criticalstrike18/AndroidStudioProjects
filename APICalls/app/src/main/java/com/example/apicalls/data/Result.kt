package com.example.apicalls.data

sealed class Result<T> (
    val data: T? = null,
    val message:String? = null
) {
    class Success<T> (data: T?): Result<T>(data)
    class Error<T> (data: T?,message: String): Result<T>(data,message)
}