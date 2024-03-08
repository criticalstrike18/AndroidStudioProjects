package com.example.authapp.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authapp.R
import com.example.authapp.ui.theme.largeTextColor
import com.example.authapp.ui.theme.textColor

@Composable
fun NormalTextComponents(value:String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .padding(0.dp, 40.dp, 0.dp, 2.dp),
        fontSize = 24 .sp,
        fontFamily = FontFamily(Font(R.font.lato_regular)),
        textAlign = TextAlign.Center,
        color = textColor

    )
}

@Composable
fun LargeTextComponents(value:String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp)
            .padding(top = 1.dp, bottom = 30.dp),
        fontSize = 30.sp,
        fontFamily = FontFamily(Font(R.font.lato_bold)),
        textAlign = TextAlign.Center,
        color = largeTextColor

    )
}
@Composable
fun ClickableTextComponent(value: String, onTextSelected : (String) -> Unit){
    val termsAndConditions = stringResource(id = R.string.terms_and_conditions)

    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(fontSize = 13.sp)){
            pushStringAnnotation(tag = "Initial-text", annotation = "Initial-text")
            append("By Continuing you are agree to our ")}
        withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = textColor, fontSize = 13.sp)){
            pushStringAnnotation(tag = termsAndConditions, annotation = "Terms and Conditions")
            append(termsAndConditions)
        }
    }
    ClickableText(text = annotatedString , onClick = {
        offset -> annotatedString.getStringAnnotations(offset,offset)
        .firstOrNull()?.also {
            span -> Log.d("ClickableTextComponent","{${span.item}}")

            if(span.item == termsAndConditions){
                onTextSelected(span.item)
            }
        }
    })
}