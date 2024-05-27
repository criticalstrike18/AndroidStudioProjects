package com.example.newsapp.presentation.onboarding.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.LightGrey
import com.example.newsapp.ui.theme.NewsAppTheme
import androidx.compose.ui.tooling.preview.Preview as Preview1


@Composable
fun NewsButton(
    text : String,
    onClick : () -> Unit
){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold))
    }
}

@Composable
fun NewsTextButton(
    text : String,
    onClick : () -> Unit
){
    TextButton(onClick = onClick) {
        Text(text = text,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
            color = LightGrey
        )
    }
}

@Preview1(showBackground = true)
@Preview1(uiMode = UI_MODE_NIGHT_YES,showBackground = true)
@Composable
fun NewsButtonPreview(){
    NewsAppTheme {
        Surface {
            NewsTextButton(text = "Back", onClick = {})
        }
    }

}