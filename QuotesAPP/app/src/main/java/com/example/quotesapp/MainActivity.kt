package com.example.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotesapp.screens.QuoteDetail
import com.example.quotesapp.screens.QuoteListScreen
import com.example.quotesapp.ui.theme.QuotesAPPTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            DataManager.loadAssetsFromFile(applicationContext)
        }
        setContent {
            QuotesAPPTheme {
                APP()
            }
        }
    }
}

@Composable
fun APP() {
    if(DataManager.isDataLoaded.value){

        if (DataManager.currentpage.value == Pages.Listing) {
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchpages(it)
            }
        }
        else{
            DataManager.currentquote?.let { QuoteDetail(quotes = it) }
        }
    }
    else{
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {
            Text(text = "Loading...",
                modifier = Modifier.padding(10.dp),
                fontFamily = FontFamily.Monospace,
                fontSize = 30.sp)
        }
    }
}

enum class Pages {
    Listing,
    Detail
}