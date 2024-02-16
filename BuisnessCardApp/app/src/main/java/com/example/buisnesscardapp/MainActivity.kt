package com.example.buisnesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buisnesscardapp.ui.theme.BuisnessCardAppTheme
import com.example.buisnesscardapp.ui.theme.syneFontFamily


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuisnessCardAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainCard()
                }
            }
        }
    }
}

@Composable
private fun CenterCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFF163020)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.android_logo
                ),
                contentDescription = "android_logo",
                contentScale = ContentScale.Crop
            )
        }
        Column(horizontalAlignment =
        Alignment.CenterHorizontally) {
            Text(
                text = stringResource(
                    id = R.string.full_name
                ),
                modifier = Modifier,
                fontSize = 35.sp,
                color = Color.Black,
                fontFamily = syneFontFamily,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(
                    id = R.string.FieldTitle
                ),
                modifier = Modifier.padding(2.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = syneFontFamily,
                fontWeight = FontWeight.Medium

            )
        }
    }
}

@Composable
private fun BottomCard() {
    Column(modifier = Modifier
        .padding(5.dp),
        ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.telephone),
                contentDescription = "Phone",
                tint = Color(0x1FF527853),
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = stringResource(
                id =R.string.phoneno),
                color = Color.Black

            )
        }
        Row {
            Icon(painter = painterResource(id = R.drawable.twitter),
                contentDescription = "socials",
                tint = Color(0x1FF527853),
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = stringResource(
                id =R.string.handle ),
                color = Color.Black,

            )
        }
        Row {
            Icon(painter = painterResource(id = R.drawable.envelopes),
                contentDescription = "email",
                tint = Color(0x1FF527853),
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = stringResource(
                id = R.string.email),
                color = Color.Black
            )
        }
    }
}

@Composable
fun MainCard(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFD5F0C1)
        ), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
        ) {
        Row(modifier = Modifier.padding(bottom = 0.dp)) {
            CenterCard()
        }
        Row {
            BottomCard()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuisnessCardAppTheme {
        MainCard()
    }
}