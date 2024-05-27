package com.example.newsapp.data.usecases

import com.example.newsapp.domain.manager.UserLocalManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val userLocalManager: UserLocalManager
) {
    suspend operator fun invoke():Flow<Boolean>{
        return userLocalManager.readAppEntry()
    }
}