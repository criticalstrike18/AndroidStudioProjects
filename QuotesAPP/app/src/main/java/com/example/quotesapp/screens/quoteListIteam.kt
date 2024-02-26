package com.example.quotesapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quotesapp.R
import com.example.quotesapp.models.quotes


@Composable
fun QuoteListItem(quotes: quotes, onClick: (quotes:quotes)-> Unit) {
    Card(
        modifier = Modifier.padding(8.dp)
            .clickable { onClick(quotes) }
    ) {
        Row(modifier = Modifier.padding(16.dp
        )) {
                Image(painter = painterResource(id = R.drawable.quote), contentDescription = "Quote logo",
                    modifier = Modifier.size(30.dp)
                        )
            Spacer(modifier = Modifier.padding(4.dp))
            Column {
                Text(text = quotes.quote,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                )
                Box(modifier = Modifier
                    .fillMaxWidth(.48f)
                    .background(Color.Gray)
                    .heightIn(1.dp)
                    .align(Alignment.End)
                )
                Text(text = quotes.author,
                    fontSize = 8.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(Font(R.font.montserrat_lightitalic))
                    )
            }
        }
    }
}

