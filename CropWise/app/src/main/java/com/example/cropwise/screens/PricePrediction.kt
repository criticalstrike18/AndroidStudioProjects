@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cropwise.screens


import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.cropwise.ViewModel.AuthViewModel
import com.google.android.play.integrity.internal.f
import com.google.android.play.integrity.internal.i


@Composable
fun PricePrediction() {
    val viewModel = AuthViewModel()

    val commodities = listOf("Barley (Jau)", "Cotton", "Maize","Potato")
    var selectedCommodity by remember { mutableIntStateOf(1) }
    var expanded by remember { mutableStateOf(false) }


    Column {
        Row(modifier = Modifier.fillMaxWidth()
            .padding(12.dp)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = commodities[selectedCommodity],
                    onValueChange = {},
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
        Row(modifier = Modifier.fillMaxWidth()
            .padding(12.dp,2.dp),
            Arrangement.Absolute.Right
        ) {
            Button(onClick = {
                viewModel.priceRequest(selectedCommodity)
            }) {
                Text("Get Recommendation")
            }
        }
        Row(Modifier.fillMaxWidth()
            .padding(0.dp,8.dp)) {
            val isLoading by remember { viewModel.shouldShowChart } // Observe directly
            val pointsData by remember { viewModel.pointsData }
            if (isLoading) {
                Log.d("PricePrediction", "isLoading is true")
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
            else if(!isLoading && pointsData.isNotEmpty()) {
                Log.d("PricePrediction", "showChart is true, pointsData is not empty")
                StraightLinechart(pointsData = pointsData)
            }
            else {
                Log.d(
                    "PricePrediction",
                    "isLoading is false, showChart is false, or pointsData is empty"
                )
            }
        }
    }
}

@Composable
fun StraightLinechart(pointsData: List<Point>) {
    val monthNames = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    val fullMonthNames = listOf(
       "0","January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun generateChartLabels(numberOfPoints: Int): List<String> {
        // Ensure numberOfPoints is within valid range
        val maxPoints = monthNames.size
        val numLabels = if (numberOfPoints <= maxPoints) numberOfPoints else maxPoints

        return monthNames.subList(0, numLabels)
    }
    val numPoints = pointsData.size
    val chartLabels = generateChartLabels(numPoints)
//    Log.d("ChartData", " ${chartLabels[0]}")
    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .steps(12)
        .labelData{i -> if (i < chartLabels.size) chartLabels[i] else ""}
        .backgroundColor(Color.Transparent)
        .shouldDrawAxisLineTillEnd(true)
        .labelAndAxisLinePadding(15.dp)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .axisLabelColor(MaterialTheme.colorScheme.primary)
        .axisLineColor(MaterialTheme.colorScheme.secondary)
        .typeFace(Typeface.SANS_SERIF)
        .build()
    val maxYValue = pointsData.maxOfOrNull { it.y }?.toDouble() ?: 0.0
    val yAxisData = AxisData.Builder()
        .steps(5)
        .labelData { i -> " ${(i * maxYValue / 5 ).toInt()}  "  }
        .labelAndAxisLinePadding(15.dp)
        .backgroundColor(Color.Transparent)
        .axisLabelColor(MaterialTheme.colorScheme.primary)
        .axisLineColor(MaterialTheme.colorScheme.primary)
        .typeFace(Typeface.SANS_SERIF)
        .build()
    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(lineType = LineType.SmoothCurve(), color = MaterialTheme.colorScheme.tertiary),
                    shadowUnderLine = ShadowUnderLine(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent
                            )
                        ), alpha = 0.3f
                    ),
//                    intersectionPoint = IntersectionPoint(color = MaterialTheme.colorScheme.primary),
                    selectionHighlightPopUp = SelectionHighlightPopUp(popUpLabel = { x, y ->
                        val xLabel = "₹${(y).toInt()}"
                        val yLabel = fullMonthNames[x.toInt()]
                        "$xLabel | $yLabel"
                    }, paddingBetweenPopUpAndPoint = 18.dp)
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Transparent
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
            .height(550.dp),
        lineChartData = data
    )
}

//val month1 = (viewModel.priceRequest(1, 1))
//val month2 = viewModel.priceRequest(1, 2)
//val month3 = viewModel.priceRequest(1, 3)
//val month4 = viewModel.priceRequest(1, 4)
//val month5 = viewModel.priceRequest(1, 5)
//val month6 = viewModel.priceRequest(1, 6)
//val month7 = viewModel.priceRequest(1, 7)
//val month8 = viewModel.priceRequest(1, 8)
//val month9 = viewModel.priceRequest(1, 9)
//val month10 = viewModel.priceRequest(1, 10)
//val month11 = viewModel.priceRequest(1, 11)
//val month12 = viewModel.priceRequest(1, 12)
//@Composable
//private fun CombinedLinechartWithBackground(pointsData: List<Point>) {
//    val steps = 5
//    val xAxisData = AxisData.Builder()
//        .axisStepSize(30.dp)
//        .steps(pointsData.size - 1)
//        .backgroundColor(MaterialTheme.colorScheme.background)
//        .labelData { i -> i.toString() }
//        .labelAndAxisLinePadding(15.dp)
//        .build()
//    val yAxisData = AxisData.Builder()
//        .steps(steps)
//        .backgroundColor(MaterialTheme.colorScheme.background)
//        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            // Add yMin to get the negative axis values to the scale
//            val yMin = pointsData.minOf { it.y }
//            val yMax = pointsData.maxOf { it.y }
//            val yScale = (yMax - yMin) / steps
//            ((i * yScale) + yMin).formatToSinglePrecision()
//        }.build()
//    val colorPaletteList = listOf<Color>(MaterialTheme.colorScheme.primary,Color.Yellow,Color.Magenta,Color.DarkGray)
//    val legendsConfig = LegendsConfig(
//        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList),
//        gridColumnCount = 4
//    )
//    val data = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsData,
//                    lineStyle = LineStyle(
//                        lineType = LineType.SmoothCurve(isDotted = true),
//                        color = colorPaletteList.first()
//                    ),
//                    shadowUnderLine = ShadowUnderLine(
//                        brush = Brush.verticalGradient(
//                            listOf(
//                                Color.Green,
//                                Color.Transparent
//                            )
//                        ), alpha = 0.3f
//                    ),
//                    selectionHighlightPoint = SelectionHighlightPoint(
//                        color = Color.Green
//                    ),
//                    selectionHighlightPopUp = SelectionHighlightPopUp(
//                        backgroundColor = Color.Black,
//                        backgroundStyle = Stroke(2f),
//                        labelColor = Color.Red,
//                        labelTypeface = Typeface.DEFAULT_BOLD
//                    )
//                ),
//                Line(
//                    dataPoints = pointsData.subList(10,20),
//                    lineStyle = LineStyle(lineType = LineType.SmoothCurve(), color = colorPaletteList[1]),
//                    intersectionPoint = IntersectionPoint(color = Color.Red),
//                    selectionHighlightPopUp = SelectionHighlightPopUp(popUpLabel = { x, y ->
//                        val xLabel = "x : ${(1900 + x).toInt()} "
//                        val yLabel = "y : ${String.format("%.2f", y)}"
//                        "$xLabel $yLabel"
//                    })
//                ),
//                Line(
//                    dataPoints = DataUtils.getLineChartData(
//                        20,
//                        start = 0,
//                        maxRange = 50
//                    ),
//                    LineStyle(color = colorPaletteList[2]),
//                    IntersectionPoint(),
//                    SelectionHighlightPoint(),
//                    shadowUnderLine = ShadowUnderLine(
//                        brush = Brush.verticalGradient(
//                            listOf(
//                                Color.Cyan,
//                                Color.Blue
//                            )
//                        ), alpha = 0.5f
//                    ),
//                    SelectionHighlightPopUp()
//                ),
//                Line(
//                    dataPoints = pointsData.subList(10, 20),
//                    LineStyle(color = colorPaletteList[3]),
//                    IntersectionPoint(),
//                    SelectionHighlightPoint(),
//                    ShadowUnderLine(),
//                    SelectionHighlightPopUp()
//                )
//            )
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(),
//        backgroundColor = Color.Yellow
//    )
//
//    Column(
//        modifier = Modifier
//            .height(400.dp)
//    ) {
//        LineChart(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp),
//            lineChartData = data
//        )
//        Legends(
//            legendsConfig = legendsConfig
//        )
//    }
//}
//val pointsData: List<Point> =
//    listOf(Point(1f, month1.toFloat()), Point(2f, 90f), Point(3f, 10f), Point(4f, 60f),Point(5f, 40f), Point(6f, 90f), Point(7f, 0f), Point(8f, 60f),Point(9f, 0f), Point(10f, 60f),Point(11f, 60f),Point(12f, 1f))
//Log.d("ChartData", "pointsData before rendering: $pointsData")