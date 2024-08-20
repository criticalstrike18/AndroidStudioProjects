package com.example.ticketyours.presentation.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ticketyours.data.model.Division
import com.example.ticketyours.data.model.LayoutData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PreferencesManager(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val CURRENT_DIVISION_NO = intPreferencesKey("current_division_no")
        val TOTAL_DIVISIONS = intPreferencesKey("total_divisions")
        val DIVISIONS_LIST = stringPreferencesKey("divisions_list")
        val LAYOUT_DATA = stringPreferencesKey("layout_data")
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

    val divisionsListFlow: Flow<List<Division>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[DIVISIONS_LIST] ?: "[]"
            Json.decodeFromString<List<Division>>(jsonString)
        }

    val layoutDataFlow: Flow<LayoutData?> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[LAYOUT_DATA]
            if (jsonString != null) {
                Json.decodeFromString<LayoutData>(jsonString)
            } else {
                null
            }
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

    suspend fun setDivisionsList(divisions: List<Division>) {
        context.dataStore.edit { preferences ->
            preferences[DIVISIONS_LIST] = Json.encodeToString(divisions)
        }
    }

    suspend fun saveLayoutData(layoutData: LayoutData) {
        context.dataStore.edit { preferences ->
            preferences[LAYOUT_DATA] = Json.encodeToString(layoutData)
        }
    }

    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Add other methods for storing and retrieving data as needed
}