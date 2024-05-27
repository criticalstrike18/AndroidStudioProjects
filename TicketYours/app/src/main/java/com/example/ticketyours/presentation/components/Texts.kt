package com.example.ticketyours.presentation.components

import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.ticketyours.ui.theme.fontFamily

@Composable
fun TitleText(value: String,modifier: Modifier){
    Text(value,
        fontFamily = fontFamily,
        fontSize = 32.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier)
}

@Composable
fun LargeText(value: String,modifier: Modifier){
    Text(value,
        fontFamily = fontFamily,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier)
}

@Composable
fun SeparateSingleText(initialText: String,
                      highlightText:String,
                      onResendCode: () -> Unit,
                      annotation: String) {
    val annotatedText = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color.Cyan)) {
            append(highlightText)
        }
        addStringAnnotation(
            tag = annotation,
            annotation = annotation,
            start = 19,
            end = 31
        )
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(annotation, offset, offset).firstOrNull()?.let {
                onResendCode()
                Log.d("TAG", annotation)
            }
        },
    )
}
