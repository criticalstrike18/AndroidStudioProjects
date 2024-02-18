package com.example.thebutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebutton.ui.theme.ThebuttonTheme
import androidx.compose.material3.ElevatedButton as ElevatedButton1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThebuttonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button_one()
                }
            }
        }
    }
}


@Composable
fun Button_one() {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 50.dp else 0.dp
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(extraPadding)) {
            Column(modifier = Modifier.weight(1f)
                .padding(bottom = extraPadding)) {
                Text(text = if (expanded.value) "Heeeeelllllooooo Woooooorrrrrrllllllldddddd " else "Hello World")
            }
            ElevatedButton1(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThebuttonTheme {
        Column {
            Button_one()
            Button_one()
        }

    }
}