@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.quotesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quotesapp.models.quotes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
@Preview
@Composable
fun QuoteListScreen(data: Array<quotes>, onClick: (quotes: quotes)->Unit){
    Column(modifier = Modifier.fillMaxWidth(),
        ) {
        Text(text = "Quotes APP",
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center,
            fontSize = 25.sp)
        QuoteList(data = data, onClick)
    }
}

