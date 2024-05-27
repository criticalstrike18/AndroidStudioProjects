package com.example.ticketyours.presentation.cache

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.ticketyours.data.model.Division

@Composable
fun rememberTempDataCache(): TempDataCache {
    return remember { TempDataCache() }
}

class TempDataCache {
    private val cache = mutableStateOf(mutableMapOf<String, Any>())

    fun put(key: String, value: Any) {
        cache.value[key] = value
    }

    fun putInt(key: String, value: Int){
        cache.value[key] = value
    }

    fun getString(key: String):String? {
        return cache.value[key] as? String
    }
    fun get(key: String): Int? {
        return cache.value[key] as? Int
    }

    fun putList(key: String, list: List<Division>) {
        cache.value[key] = list
    }
    @Suppress("UNCHECKED_CAST")
    fun getList(key: String): List<Division>? {
        return cache.value[key] as? List<Division>
    }

    // Add an item of type Division to the list associated with the key
    fun addToList(key: String, item: Division) {
        val currentList = getList(key)?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        putList(key, currentList)
    }
    fun remove(key: String) {
        cache.value.remove(key)
    }

    fun clear() {
        cache.value.clear()
    }
}
