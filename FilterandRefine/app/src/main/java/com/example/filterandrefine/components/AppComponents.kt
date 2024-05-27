@file:Suppress("NAME_SHADOWING")

package com.example.filterandrefine.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.filterandrefine.R
import com.example.filterandrefine.data.ProfileData
import com.example.filterandrefine.navigation.ScreenRoute


@Composable
fun TopHeader(navController: NavHostController){
    val interactionSource = remember { MutableInteractionSource() }
    Row(modifier = Modifier
        .background(color = colorResource(id = R.color.darkBlue))
        .fillMaxWidth()
        .padding(top = 28.dp, start = 16.dp)) {
        Icon(imageVector = Icons.Filled.Dehaze,
            contentDescription = "SideRail",
            modifier = Modifier
                .padding(start = 6.dp, end = 8.dp, top = 10.dp)
                .size(40.dp),
            colorResource(id = R.color.white)
        )
        Column(modifier = Modifier.padding(top = 3.dp, start = 8.dp, end = 8.dp)) {
            Box {
                Text(
                    text = "Howdy Saksham !!", modifier = Modifier
                        .padding(top = 6.dp, start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.white)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 6.dp,top = 26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn, contentDescription = "LocationIcon",
                        modifier = Modifier
                            .padding(start = 2.dp, end = 2.dp)
                            .size(16.dp),
                        colorResource(id = R.color.white)
                    )
                    Text(
                        text = "Talegaon, Pune", fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, top = 6.dp)
                .clickable(
                    onClick = {
                        navController.navigate(ScreenRoute.RefineScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    indication = null,
                    interactionSource = interactionSource
                )
                .scale(
                    scale = if (interactionSource.collectIsPressedAsState().value) 0.92f else 1f
                ),
            horizontalAlignment = Alignment.End
                ) {
            Box {
                Icon(
                    imageVector = Icons.Outlined.Checklist, contentDescription = "RefineIcon",
                    modifier = Modifier
                        .size(35.dp),
                    colorResource(id = R.color.white)
                )
                Text(text = "Refine", fontSize = 12.sp, modifier = Modifier.padding(top = 28.dp), color = colorResource(id = R.color.white))
            }

        }

    }
}

@Composable
fun RefineTitleHeader(navController: NavHostController,title: String,icon: ImageVector){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.mediumBlue))) {
        Row(
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.padding(start = 6.dp, top = 32.dp, bottom = 8.dp)
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            Icon(
                imageVector = icon, contentDescription = title,
                modifier = Modifier
                    .padding(start = 20.dp, end = 8.dp, top = 15.dp)
                    .size(20.dp)
                    .clickable(
                        onClick = { navController.navigate(ScreenRoute.ExploreScreen.route) },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 25.dp
                        ),
                        interactionSource = interactionSource
                    ),
                colorResource(id = R.color.white)
            )
            Text(
                text = title, fontSize = 26.sp,
                modifier = Modifier.padding(start = 20.dp, top = 8.dp),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white)
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
            color = colorResource(id = R.color.darkBlue)
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
            color = colorResource(id = R.color.darkBlue)
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
            color = colorResource(id = R.color.darkBlue)
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
            color = colorResource(id = R.color.darkBlue)
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
            onExpandedChange = { expanded = !expanded },
            Modifier.background(Color.White)
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
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedIndicatorColor = colorResource(id = R.color.blueGrey),
                    focusedIndicatorColor = colorResource(id = R.color.darkBlue),
                    unfocusedLabelColor = colorResource(id = R.color.darkBlue),
                    unfocusedPlaceholderColor = colorResource(id = R.color.darkBlue),
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .background(colorResource(id = R.color.white)),
                offset = DpOffset(8. dp,2.dp),
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
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = colorResource(id = R.color.blueGrey),
                focusedIndicatorColor = colorResource(id = R.color.darkBlue),
                unfocusedLabelColor = colorResource(id = R.color.darkBlue),
                unfocusedPlaceholderColor = colorResource(id = R.color.darkBlue),
            )
        )

        Text(
            text = "${text.length} / $maxChar",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, end = 4.dp),
            color = colorResource(id = R.color.mediumBlue),
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
                thumbColor = colorResource(id = R.color.darkBlue),
                activeTrackColor = colorResource(id = R.color.mediumBlue),
                inactiveTrackColor = colorResource(id = R.color.blueGrey),
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
            ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.darkBlue))
        } else {
            ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white))
        },
        border = if (isSelected) {
            null
        } else {
            BorderStroke(1.dp, colorResource(id = R.color.mediumBlue))
        },
        contentPadding = PaddingValues(horizontal = 13.dp, vertical = 4.dp),
    ) {
        Text(title,
            color = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else colorResource(id = R.color.darkBlue),
            fontSize = 12.sp)
    }
}

@Composable
fun SaveButton(text: String, onClick: () -> Unit) { // onClick is a lambda function
    Button(
        onClick = onClick, // Call the provided function when the button is clicked
        modifier = Modifier.padding(start = 135.dp,top = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.darkBlue))
    ) {
        Text(text = text)
    }
}

@Composable
fun ProfileCard(profileData: ProfileData) {
    Box(modifier = Modifier.padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp, end = 8.dp, top = 12.dp)
                .border(
                    width = 1.dp, color = colorResource(id = R.color.blueGrey),
                    shape = RoundedCornerShape(8)
                ),
            shape = RoundedCornerShape(8),
            colors = CardDefaults.cardColors(
                containerColor =  colorResource(id = R.color.white)
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(colorResource(id = R.color.white))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 55.dp)
                    ) {
                        Text(
                            text = profileData.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "${profileData.location} | ${profileData.designation}",color = colorResource(id = R.color.mediumBlue) ,fontSize = 14.sp,fontWeight = FontWeight.Bold)
                        Text(text = "Within ${profileData.distance} m", fontSize = 12.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LinearProgressIndicator(
                                progress = { profileData.profileScore / 100f },
                                color = colorResource(id = R.color.blueGrey),
                                trackColor = MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 2.dp)
                                    .weight(1f),
                                strokeCap = StrokeCap.Round
                            )
                            Text(
                                text = "ProfileScore- ${profileData.profileScore}%",
                                modifier = Modifier
                                    .padding(start = 6.dp),
                                fontSize = 10.sp,
                                color = colorResource(id = R.color.mediumBlue)
                            )
                        }
                    }
                    Column {
                        Text(text = "+ INVITE", fontWeight = FontWeight.Bold,color = colorResource(id = R.color.darkBlue))
                    }

                }
                Spacer(modifier = Modifier.height(4.dp))

                // Interests
                Row(verticalAlignment = Alignment.CenterVertically) {
                    InterestChip(text = "Coffee", icon = Icons.Outlined.Coffee,colorResource(id = R.color.darkBrown))
                    Text(text = "|", color = colorResource(id = R.color.blueGrey))
                    InterestChip(text = "Friendship", icon = Icons.Outlined.People,colorResource(id = R.color.Yellow))
                    Text(text = "|", color = colorResource(id = R.color.blueGrey))
                    InterestChip(text = "Dating", icon = Icons.Outlined.Favorite,Color.Red)
                }
                Text(text = profileData.bio, modifier = Modifier.padding(horizontal = 8.dp) ,fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.mediumBlue))
            }
        }
        Column {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .size(95.dp)
                    .background(
                        colorResource(id = R.color.blueGrey),
                        shape = RoundedCornerShape(25)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = profileData.initials,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.darkBlue)
                )
            }
        }
    }
}

@Composable
fun InterestChip(text: String, icon: ImageVector,composableColor: Color) {


    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical =2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(16.dp),composableColor)
        Text(text = text, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp),color = composableColor)
    }
}

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String = "Search",
    backgroundColor: Color = colorResource(id = R.color.white),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = colorResource(id = R.color.mediumBlue),
    focusedBorderColor: Color = colorResource(id = R.color.darkBlue)
) {
    var isFocused by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8), // Rounded corners
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(.4f)
                    .height(36.dp)
                    .border(
                        width = 1.dp, color = colorResource(id = R.color.mediumBlue),
                        shape = RoundedCornerShape(20)
                    )
                    .background(
                        color = Color.Transparent, // Transparent background
                        shape = RoundedCornerShape(80) // Rounded corners
                    )
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)) { // Row for icon and text
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = textColor // Match icon color to text color,
                        )
                        Spacer(modifier = Modifier.width(10.dp)) // Add some space between icon and text
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (searchText.isEmpty()) { // Show placeholder when empty
                                Text(
                                    text = placeholderText, // Dim placeholder color
                                    fontSize = 15.sp
                                )
                            }
                            innerTextField() // Render the actual text field
                        }
                    }
                }
            )
        }
    }
}