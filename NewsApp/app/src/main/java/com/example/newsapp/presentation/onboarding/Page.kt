package com.example.newsapp.presentation.onboarding

import android.icu.text.CaseMap.Title
import androidx.annotation.DrawableRes
import com.example.newsapp.R

data class Page(
    val title: String,
    val content: String,
    @DrawableRes val imageResId: Int
)

val pages = listOf(
    Page("Welcome!", "Dive into the world of news at your fingertips. Stay informed, stay enlightened.", R.drawable.onboarding1),
    Page("Customize Your Feed!", "Tailor your news experience with topics From global events to local updates, it’s all here.",R.drawable.onboarding2),
    Page("Stay Updated On The Go!", "Quick, reliable, and always with you. Get the latest news with a tap, even when you’re offline.",R.drawable.onboarding3)

)