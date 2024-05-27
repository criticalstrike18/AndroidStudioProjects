package com.example.ticketyours.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ticketyours.data.model.Division
import com.example.ticketyours.data.model.DivisionData
import com.example.ticketyours.data.model.LayoutData
import com.example.ticketyours.data.model.RowData
import com.example.ticketyours.data.model.Seat
import com.example.ticketyours.data.model.SeatData
import com.example.ticketyours.presentation.cache.rememberTempDataCache
import com.example.ticketyours.presentation.components.SideButton
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Composable
fun SeatLayoutPage(
    navController: NavController,
    currentScreenNo: Int,
    totalScreens: Int,
    key: String,
    maxSeatsPerRow: Int
){
    val tempCache = rememberTempDataCache()
    val divisions = tempCache.getList(key)!!
    var currentSeats by remember { mutableStateOf(generateInitialSeats(divisions, maxSeatsPerRow)) }
    var currentMaxSeats by remember { mutableIntStateOf(maxSeatsPerRow) }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            if(currentMaxSeats > maxSeatsPerRow){
                SideButton(
                    imageVector = Icons.Filled.Remove,
                    modifier = Modifier
                        .weight(1f),
                    Description = "Reduce seats",
                    onClick = {
                        if (currentMaxSeats > maxSeatsPerRow) {
                            currentMaxSeats--
                            currentSeats = updateSeatsForNewMax(currentSeats, currentMaxSeats)
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    divisions.forEachIndexed { divIndex, division ->
                        item {
                            Text(
                                text = division.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                                    .align(Alignment.Center)
                                    .background(Color.Green)
                            )
                        }
                        items(division.rows) { rowIndex ->
                            val globalRowIndex = divisions.take(divIndex).sumOf { it.rows } + rowIndex
                            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                repeat(currentMaxSeats) { columnIndex ->
                                    val seat = currentSeats.getOrNull(globalRowIndex)?.getOrNull(columnIndex)
                                    Icon(
                                        imageVector = Icons.Filled.EventSeat,
                                        contentDescription = "Seat",
                                        tint = if (seat?.isMarked == true) Color.Transparent else Color.Green,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(2.dp)
                                            .clickable {
                                                if (seat != null) {
                                                    currentSeats =
                                                        currentSeats.mapIndexed { rowIdx, row ->
                                                            if (rowIdx == globalRowIndex) {
                                                                row.mapIndexed { colIdx, s ->
                                                                    if (colIdx == columnIndex) s.copy(
                                                                        isMarked = !s.isMarked
                                                                    ) else s
                                                                }
                                                            } else row
                                                        }
                                                }
                                            }
                                    )
                                }
                            }
                        }

                    }
                }
            }
            SideButton(
                imageVector = Icons.Filled.Add,
                modifier = Modifier
                    .weight(1f),
                Description = "Add Seats",
                onClick = {
                    currentMaxSeats++
                    currentSeats = updateSeatsForNewMax(currentSeats, currentMaxSeats)
                }
            )
        }
        Button(
            onClick = { saveLayoutToJson(divisions, currentSeats, currentMaxSeats,context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Complete Layout")
        }
    }
}

fun updateSeatsForNewMax(currentSeats: List<List<Seat>>, newMaxSeats: Int): List<List<Seat>> {
    return currentSeats.map { row ->
        if (row.size < newMaxSeats) {
            row + List(newMaxSeats - row.size) {
                Seat(row.first().division, row.first().row, it + row.size)
            }
        } else {
            row.take(newMaxSeats)
        }
    }
}

fun generateInitialSeats(divisions: List<Division>, maxSeatsPerRow: Int): List<List<Seat>> {
    return divisions.flatMapIndexed { _, division ->
        (0 until division.rows).map { rowIndex ->
            (0 until maxSeatsPerRow).map { columnIndex ->
                Seat(division.name, rowIndex, columnIndex)
            }
        }
    }
}

fun saveLayoutToJson(divisions: List<Division>, seats: List<List<Seat>>, maxSeatsPerRow: Int, context: Context) {
    val layoutData = LayoutData(
        divisions = divisions.mapIndexed { divIndex, division ->
            DivisionData(
                name = division.name,
                rows = seats.subList(
                    divisions.take(divIndex).sumOf { it.rows },
                    divisions.take(divIndex + 1).sumOf { it.rows }
                ).map { row ->
                    RowData(
                        seats = row.map { seat ->
                            SeatData(seat.division, seat.row, seat.column, seat.isMarked)
                        }
                    )
                }
            )
        },
        maxSeatsPerRow = maxSeatsPerRow
    )
    val json = Json.encodeToString(layoutData)

    try {
        val file = File(context.filesDir, "layout.json")
        file.writeText(json)
        Toast.makeText(context, "Json File Saved", Toast.LENGTH_SHORT).show()
        // Optionally, show a success message to the user
    } catch (e: Exception) {
        // Handle the error, perhaps show an error message to the user
        e.printStackTrace()
    }
}

//val div1 = Division("Platinum", 15)
//val div2 = Division("Gold", 1)
//val div3 = Division("Silver", 1)
//val divList = listOf(div1, div2, div3)
