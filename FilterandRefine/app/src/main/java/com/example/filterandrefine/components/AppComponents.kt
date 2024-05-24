package com.example.filterandrefine.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.filterandrefine.navigation.ScreenRoute


@Composable
fun TopHeader(navController: NavHostController){
    val interactionSource = remember { MutableInteractionSource() }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp)) {
        Icon(imageVector = Icons.Filled.Dehaze,
            contentDescription = "SideRail",
            modifier = Modifier
                .padding(start = 6.dp, end = 8.dp, top = 10.dp)
                .size(40.dp),
        )
        Column(modifier = Modifier.padding(top = 3.dp, start = 8.dp, end = 8.dp)) {
            Box {
                Text(
                    text = "Howdy Saksham !!", modifier = Modifier
                        .padding(top = 6.dp, start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 6.dp,top = 26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn, contentDescription = "LocationIcon",
                        modifier = Modifier
                            .padding(start = 2.dp, end = 2.dp)
                            .size(16.dp)
                    )
                    Text(
                        text = "Talegaon, Pune", fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, top = 6.dp)
                .clickable(onClick = {navController.navigate(ScreenRoute.RefineScreen.route) {
                    launchSingleTop = true
                }},
                    indication = null,
                    interactionSource = interactionSource)
                .scale(
                    scale = if (interactionSource.collectIsPressedAsState().value) 0.92f else 1f
                ),
            horizontalAlignment = Alignment.End
                ) {
            Box {
                Icon(
                    imageVector = Icons.Outlined.Checklist, contentDescription = "RefineIcon",
                    modifier = Modifier
                        .size(35.dp)
                )
                Text(text = "Refine", fontSize = 12.sp, modifier = Modifier.padding(top = 28.dp))
            }

        }

    }
}

@Composable
fun RefineTitleHeader(navController: NavHostController){
    Column(modifier = Modifier
        .padding(top = 12.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)) {
        Row(
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.padding(start = 6.dp, top = 26.dp)
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "LocationIcon",
                modifier = Modifier
                    .padding(start = 20.dp, end = 8.dp, top = 15.dp)
                    .size(20.dp)
                    .clickable(onClick = { navController.navigate(ScreenRoute.ExploreScreen.route) },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 25.dp
                            ),
                            interactionSource = interactionSource)
            )
            Text(
                text = "Refine", fontSize = 26.sp,
                modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TitledDropDown(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select Your Availability", fontSize = 14.sp,
            modifier = Modifier.padding(start = 28.dp, top = 8.dp, bottom = 6.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        OutlinedDropDownMenu()
    }
}

@Composable
fun TitledTextBox(){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Text(
            text = "Add Your Status", fontSize = 14.sp,
            modifier = Modifier.padding(start = 28.dp, top = 6.dp, bottom = 6.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        LiveTextFieldWithCharCount()
    }
}

@Composable
fun TitledSlider(){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Text(
            text = "Select Hyper local Distance", fontSize = 14.sp,
            modifier = Modifier.padding(start = 28.dp, top = 6.dp, bottom = 6.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        DistanceSlider()
    }
}
@Composable
fun PurposeSelection(){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Text(
            text = "Select Purpose", fontSize = 14.sp,
            modifier = Modifier.padding(start = 28.dp, top = 12.dp, bottom = 4.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Row(modifier = Modifier.padding(start = 28.dp)) {
            ToggleableButton(title = "Coffee", onToggle = { })
            ToggleableButton(title = "Business", onToggle = { })
            ToggleableButton(title = "Hobbies", onToggle = { })
            ToggleableButton(title = "Friendship", onToggle = { })
        }
        Row(modifier = Modifier.padding(start = 28.dp)) {
            ToggleableButton(title = "Movies", onToggle = { })
            ToggleableButton(title = "Dinning", onToggle = { })
            ToggleableButton(title = "Dating", onToggle = { })
            ToggleableButton(title = "Matrimony", onToggle = { })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedDropDownMenu() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Available | Hey Let Us Connect",
        "Away | Stay Discrete And Watch",
        "Busy | Do Not Disturb | Will Catch Up Later",
        "HELP Assistance! Need Emergency! |SOS")
    var selectedItem by remember { mutableIntStateOf(0) }

    Column(Modifier.padding(start = 28.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = items[selectedItem],
                onValueChange = { },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                readOnly = true,
                singleLine = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                textStyle = TextStyle(fontSize = 14.sp),
                shape = RoundedCornerShape(20.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .padding(8.dp),
                offset = DpOffset(8. dp,2.dp)
            ) {
                items.forEachIndexed { index, text ->
                    DropdownMenuItem(text = { Text(text, fontSize = 12.sp) },
                        onClick = {
                            selectedItem = index
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LiveTextFieldWithCharCount() {
    var text by remember { mutableStateOf("") }
    val maxChar = 250

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { if (it.length <= maxChar) text = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            shape = RoundedCornerShape(20.dp),
        )

        Text(
            text = "${text.length} / $maxChar",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, end = 4.dp),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DistanceSlider() {
    var sliderValue by remember { mutableFloatStateOf(1f) }

    Column(
        modifier = Modifier.padding(start = 26.dp,end =4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 1f..100f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.inversePrimary,
                activeTickColor = MaterialTheme.colorScheme.primaryContainer,
                inactiveTickColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Display the kilometer value inside the thumb
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart // Align to the left edge of the thumb
        ) {
            Text(
                text = "${sliderValue.toInt()} km",
                modifier = Modifier.offset(x = (sliderValue - 1) / 99 * 300.dp), // Adjust offset based on slider width
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ToggleableButton(title: String,
                     onToggle: (Boolean) -> Unit, // Callback for toggle changes
                     enabled: Boolean = true,
                     initialSelected: Boolean = false,) {
    var isSelected by remember { mutableStateOf(initialSelected) }
    Button(
        onClick = {
            isSelected = !isSelected
            onToggle(isSelected)
        },
        modifier = Modifier.padding(4.dp),
        enabled = enabled,
        shape = RoundedCornerShape(75),
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        } else {
            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
        },
        border = if (isSelected) {
            null
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        },
        contentPadding = PaddingValues(horizontal = 13.dp, vertical = 4.dp),
    ) {
        Text(title,
            color = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.primary,
            fontSize = 12.sp)
    }
}

@Composable
fun SaveButton(text: String, onClick: () -> Unit) { // onClick is a lambda function
    Button(
        onClick = onClick, // Call the provided function when the button is clicked
        modifier = Modifier.padding(start = 135.dp,top = 16.dp),
    ) {
        Text(text = text)
    }
}