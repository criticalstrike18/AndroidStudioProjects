package com.example.newsapp.data.usecases

import com.example.newsapp.domain.manager.UserLocalManager

class SaveAppEntry(
    private val userLocalManager: UserLocalManager
) {
    suspend operator fun invoke() = userLocalManager.saveAppEntry()
}