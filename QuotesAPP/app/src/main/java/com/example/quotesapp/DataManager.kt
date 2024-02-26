package com.example.quotesapp

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.quotesapp.models.quotes
import com.google.gson.Gson
import java.nio.charset.Charset

object DataManager {
    var data = emptyArray<quotes>()
    var currentquote: quotes? = null
    var currentpage = mutableStateOf(Pages.Listing)
    var isDataLoaded = mutableStateOf(false)
    fun loadAssetsFromFile(context: Context){
        val inputStream = context.assets.open("quotes.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charset.defaultCharset())
        val gson = Gson()
        data = gson.fromJson(json, Array<quotes>::class.java)
        isDataLoaded.value = true
    }
    fun switchpages(quotes: quotes?) {
        if(currentpage.value == Pages.Listing){
            currentquote = quotes
            currentpage.value = Pages.Detail
        }
        else{
            currentpage.value = Pages.Listing
        }
    }

}

