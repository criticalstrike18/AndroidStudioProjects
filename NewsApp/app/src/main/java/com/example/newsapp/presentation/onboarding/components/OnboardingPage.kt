package com.example.newsapp.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.newsapp.R
import com.example.newsapp.presentation.Dimens.MediumPadding1
import com.example.newsapp.presentation.Dimens.MediumPadding2
import com.example.newsapp.presentation.onboarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page : Page
){
    Column(modifier =  modifier) {
        Image(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f),
            painter = painterResource(id = page.imageResId),
            contentDescription = page.title,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(text = page.title,
            modifier = Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small))
        Text(text = page.content,
            modifier = Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium))
        Spacer(modifier = Modifier.height(MediumPadding1))
    }
}
