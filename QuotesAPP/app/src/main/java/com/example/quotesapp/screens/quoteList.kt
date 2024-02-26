package com.example.quotesapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.quotesapp.models.quotes

@Composable
fun QuoteList(data:Array<quotes>, onClick: (quotes: quotes)-> Unit) {
    LazyColumn(content = {
        items(data){
            QuoteListItem(quotes = it, onClick)
        }
    })
}