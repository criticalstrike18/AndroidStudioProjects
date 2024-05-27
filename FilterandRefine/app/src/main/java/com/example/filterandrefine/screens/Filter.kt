package com.example.filterandrefine.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.filterandrefine.components.PurposeSelection
import com.example.filterandrefine.components.RefineTitleHeader
import com.example.filterandrefine.components.SaveButton
import com.example.filterandrefine.components.TitledDropDown
import com.example.filterandrefine.components.TitledSlider
import com.example.filterandrefine.components.TitledTextBox
import com.example.filterandrefine.navigation.ScreenRoute

@Composable
fun FilterScreen(navController: NavHostController){
    Column(modifier = Modifier.run {
        fillMaxSize()
        .clickable(onClick = {},
            indication = null,
            interactionSource = remember { MutableInteractionSource() })
    }
    ) {
        RefineTitleHeader(navController,"Filter", Icons.Filled.Close)
        HorizontalDivider(modifier = Modifier.padding(10.dp)
            .fillMaxWidth())
        TitledDropDown()
        TitledTextBox()
        TitledSlider()
        PurposeSelection()
        Spacer(modifier = Modifier.padding(18.dp))
        SaveButton(text = "Save & Explore") { navController.navigate(ScreenRoute.ExploreScreen.route) }
    }

}