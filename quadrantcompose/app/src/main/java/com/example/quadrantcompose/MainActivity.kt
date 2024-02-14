package com.example.quadrantcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quadrantcompose.ui.theme.QuadrantcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuadrantcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    QuadrantCompose()
                }
            }
        }
    }
}

@Composable
fun Quadrant(title: String,description: String, bgcolor: Color, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(bgcolor)
            .padding(16.dp)
            .fillMaxWidth()) {
        Text(text = title,
            modifier = modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(text = description,
            textAlign = TextAlign.Justify,
            color = Color.Black
        )
    }
}
@Composable
fun QuadrantCompose() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.weight(1f)) {
            Quadrant(title = stringResource(id = R.string.title1) , description = stringResource(
                id = R.string.des1), bgcolor = Color(0xFFEADDFF), modifier = Modifier.weight(1f))
            Quadrant(title = stringResource(id = R.string.title2), description = stringResource(
                id = R.string.description2) , bgcolor = Color(0xFFD0BCFF), modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.weight(1f)) {
            Quadrant(title = stringResource(id = R.string.title3), description = stringResource(
                id = R.string.description3), bgcolor = Color(0xFFB69DF8), modifier = Modifier.weight(1f))
            Quadrant(title = stringResource(id = R.string.title4), description = stringResource(
                id = R.string.description4), bgcolor = Color(0xFFF6EDFF), modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuadrantcomposeTheme {

    }
}