package com.example.quotesapp.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quotesapp.DataManager
import com.example.quotesapp.R
import com.example.quotesapp.models.quotes


@Composable
fun QuoteDetail(quotes: quotes) {

    BackHandler {
        DataManager.switchpages(null)
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.sweepGradient(
                    colors = listOf(
                        Color.Gray,
                        Color.White
                    )
                )
            )) {
        Card(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.padding(6.dp)) {
                Image(painter = painterResource(id = R.drawable.quote),
                    contentDescription = "quotes",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp, 0.dp, 0.dp, 5.dp))
                Text(text = quotes.quote,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = quotes.author,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth())
            }
        }
    }
}