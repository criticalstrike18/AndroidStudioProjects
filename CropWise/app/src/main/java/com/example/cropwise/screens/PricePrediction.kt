@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cropwise.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import com.example.cropwise.R
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.components.CircleLoader
import com.example.cropwise.components.StraightLinechart


@Composable
fun PricePrediction() {
    val viewModel = AuthViewModel()
    val commodities = listOf("Barley (Jau)", "Cotton", "Maize", "Potato")
    var selectedCommodity by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val pointsData0 by remember { viewModel.pointsData0 }
    val pointsData1 by remember { viewModel.pointsData1 }
    val pointsData2 by remember { viewModel.pointsData2 }
    val pointsData3 by remember { viewModel.pointsData3 }
    val isLoading by remember { viewModel.loading }




    Column {
        Row(modifier = Modifier.fillMaxWidth(), // Make Row occupy full width
            horizontalArrangement = Arrangement.SpaceBetween // Distribute items with space in between
        ){
            Text(
                text = "Price Prediction ",
                fontFamily = FontFamily(Font(R.font.robotocondensed_semibold)),
                fontSize = 35.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()
            .padding(8.dp,1.dp), // Make Row occupy full width
            horizontalArrangement = Arrangement.SpaceBetween // Distribute items with space in between
        ){
            Divider()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = commodities[selectedCommodity],
                    onValueChange = { },
                    label = { Text("Select an Option") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                // Display list of options
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    commodities.forEachIndexed { index, text ->
                        DropdownMenuItem(text = { Text(text) },
                            onClick = {
                                selectedCommodity = index
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp, 2.dp),
//            Arrangement.Absolute.Right
//        ) {
//            Button(onClick = {
//                when (selectedCommodity) {
//                    0 -> viewModel.priceRequest()
//                    1 -> viewModel.priceRequest1()
//                    2 -> viewModel.priceRequest()
//                }
//            }) {
//                Text("Get Recommendation")
//            }
//        }

        LaunchedEffect(key1 = selectedCommodity) {
            Log.d("launch","triggered")
            when (selectedCommodity) {
                0 -> viewModel.priceRequest()
            }
        }

        Row {
            when (selectedCommodity) {
                0 -> ChartRow(isLoading = isLoading, pointsData = pointsData0) // Barley chart
                1 -> ChartRow(isLoading = isLoading, pointsData = pointsData1) // Cotton chart
                2 -> ChartRow(isLoading = isLoading, pointsData = pointsData2) // Maize chart
                3 -> ChartRow(isLoading = isLoading, pointsData = pointsData3) // Potato chart
            }
        }
    }
}

@Composable
fun ChartRow(isLoading: Boolean,pointsData:List<Point>){
    if(isLoading){
        Log.d("LoadingPriceData",pointsData.toString())
        CircleLoader()
    }
    else if (!isLoading && pointsData.isNotEmpty()){
        Log.d("PriceData",pointsData.toString())
        StraightLinechart(pointsData = pointsData)
        Log.d("LoadingData",isLoading.toString())
    }
}