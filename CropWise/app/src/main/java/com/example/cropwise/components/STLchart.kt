package com.example.cropwise.components

import android.graphics.Typeface
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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