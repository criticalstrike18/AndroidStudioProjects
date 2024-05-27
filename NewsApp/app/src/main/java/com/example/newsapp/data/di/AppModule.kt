package com.example.newsapp.data.di

import android.app.Application
import com.example.newsapp.data.manager.UserLocalManagerImpl
import com.example.newsapp.data.usecases.AppEntryUseCases
import com.example.newsapp.data.usecases.ReadAppEntry
import com.example.newsapp.data.usecases.SaveAppEntry
import com.example.newsapp.domain.manager.UserLocalManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserLocalManager(
        app: Application
    ): UserLocalManager = UserLocalManagerImpl(app)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        userLocalManager: UserLocalManager
    ) = AppEntryUseCases(
       readAppEntry = ReadAppEntry(userLocalManager),
        saveAppEntry = SaveAppEntry(userLocalManager)
    )
}