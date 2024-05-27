package com.example.ticketyours.presentation.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val CURRENT_DIVISION_NO = intPreferencesKey("current_division_no")
        val TOTAL_DIVISIONS = intPreferencesKey("total_divisions")
        // Add other keys as needed
    }

    val currentDivisionNoFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[CURRENT_DIVISION_NO] ?: 1
        }

    val totalDivisionsFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[TOTAL_DIVISIONS] ?: 1
        }

    suspend fun setCurrentDivisionNo(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_DIVISION_NO] = value
        }
    }

    suspend fun setTotalDivisions(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[TOTAL_DIVISIONS] = value
        }
    }

    // Add other methods for storing and retrieving data as needed
}