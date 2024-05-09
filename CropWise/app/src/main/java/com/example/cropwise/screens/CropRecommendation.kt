@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.cropwise.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cropwise.R
import com.example.cropwise.ViewModel.AuthViewModel
import com.example.cropwise.components.CardDesc
import com.example.cropwise.components.CardDesc2
import com.example.cropwise.components.CardHeading
import com.example.cropwise.components.CardSubHeading
import com.example.cropwise.components.CardSubHeading2
import com.example.cropwise.components.CircleLoader
import com.example.cropwise.components.CircularBulletList
import com.example.cropwise.model.CropData

@Composable
fun CropRecommendations(viewModel: AuthViewModel,navController: NavHostController) {
    val context = LocalContext.current
    val recommendation by viewModel.cropRecommendation.observeAsState("") // Observe state
    Log.d("CropRecom", "LiveData value updated: $recommendation")
    var isLoading by remember { mutableStateOf(false) } // State to track loading

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (!isLoading) {  // Show button when not loading
            Column {
                Box(modifier = Modifier
                    .padding(30.dp)
                    .fillMaxSize()) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .align(Alignment.Center) // Align the card to the center
                            .fillMaxWidth() // Make the card take full width
                            .clickable {
                                viewModel.sendCropRequest(navController, context)
                                isLoading = true
                            }
                    ) {
                        CardHeading(value = "Crop Recommendation")
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = stringResource(R.string.croprecomdesc),
                            modifier =Modifier.padding(16.dp, 2.dp),
                            fontFamily = FontFamily(Font(R.font.poppins_light)),)

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween){
                            Spacer(modifier = Modifier.weight(1f))
                            Button(onClick = {
                                viewModel.sendCropRequest(navController,context)
                                isLoading = true // Start loading state
                            }) {
                                Text("Get Recommendation")
                            }
                        }
                    }
                }
            }
        }

        if (isLoading && recommendation == null ) {   // Show progress indicator when loading
            CircleLoader()
        }

        if (recommendation != null) { // Display recommendation if available
            AdvancedCropCarousel(recommendation!!)
        }
    }

}

val CropList = listOf(
    CropData("mothbeans",R.drawable.mothbeans,R.string.mothbeans,R.string.mothbeansdesc,R.string.mothbeansfertreq,(listOf("Compost","Farm Yard Manure (FYM)","Vermicompost","Bone Meal")),(listOf("Liquid Seaweed Fertilizer","Compost Tea","Neem Cake","Cow Manure"))),
    CropData("coffee",R.drawable.coffee,R.string.coffee,R.string.coffeedesc,R.string.coffeefertreq,(listOf("Compost","Worm Castings","Vermicompost","Bone Meal")),(listOf("Coffee Grounds","Compost Tea","Fish Emulsion","Manure (well-aged)"))),
    CropData("rice",R.drawable.rice,R.string.rice,R.string.ricedesc,R.string.ricefertreq,(listOf("Compost","Animal Manure(well rotted)","Vermicompost","Green Manure")),(listOf("Azolla","Compost Tea","Fish Emulsion","Bone Meal"))),
    CropData("chickpea",R.drawable.chickpea,R.string.chickpeas,R.string.chickpeadesc,R.string.chickpeafertreq,(listOf("Compost","Well-rotted Manure","Bone Meal","Neem Cake")),(listOf("Vermicompost","Compost Tea","Fish Emulsion","Wood Ash"))),
    CropData("cotton",R.drawable.cotton,R.string.cotton,R.string.cottondesc,R.string.cottonfertreq,(listOf("Compost","Manure","Bone Meal","Fish Meal")),(listOf("Seaweed Extract","Compost Tea","Fish Emulsion","Worm Castings"))),
    CropData("maize",R.drawable.maize,R.string.maize,R.string.maizedesc,R.string.maizefertreq,(listOf("Compost","Manure","Bone Meal","Blood Meal")),(listOf("Compost Tea","Fish Emulsion","Worm Castings"))),
    CropData("coconut",R.drawable.coconut,R.string.coconut,R.string.coconutdesc,R.string.coconutfertreq,(listOf("Compost","Coir Pith Compost","Farmyard Manure (FYM)")),(listOf("Green Manures","Vermicompost","Neem Cake","Bone Meal","Seaweed Extract"))),
    CropData("blackgram",R.drawable.blackgram,R.string.blackgram,R.string.blackgramdesc,R.string.blackgramfertreq,(listOf("Compost","Vermicompost","Neem Cake")),(listOf("Leguminous Cover Crops","Manure","Fish Emulsion"))),
    CropData("jute",R.drawable.jute,R.string.jute,R.string.jutedesc,R.string.jutefertreq,(listOf("Compost","Vermicompost","Farmyard Manure (FYM)")),(listOf("Liquid Manure","Mustard Cake or Neem Cake","Bone Meal","Wood Ash"))),
    CropData("kidneybeans",R.drawable.kidneybeans,R.string.kidneybeans,R.string.kidneybeansdesc,R.string.kidneybeansfertreq,(listOf("Compost","Vermicompost","Manure","Bone Meal","Fish Meal")),(listOf("Kelp Meal","Worm Castings","Compost Tea"))),
    CropData("pigeonpeas",R.drawable.pigeonspeas,R.string.pigeon_peas,R.string.pigeonpeasdesc,R.string.pigeonpeasfertreq,(listOf("Compost","Rock Phosphate","Bone Meal","Farmyard Manure (FYM)")),(listOf("Vermicompost","Fish Emulsion/Seaweed Extract","Compost Tea"))),
    CropData("orange", R.drawable.orange, R.string.oranges, R.string.orangedesc, R.string.orangefertreq, (listOf("Compost", "Manure", "Bone Meal")), (listOf("Fish Emulsion", "Seaweed Extract", "Worm Castings"))),
    CropData("pomegranate", R.drawable.pomegranate, R.string.pomegranate, R.string.pomegranatedesc, R.string.pomegranatefertreq, (listOf("Compost", "Manure","Bone Meal")), (listOf("Fish Emulsion", "Neem Cake", "Compost Tea","Vermicompost"))),
    CropData("apple", R.drawable.apple, R.string.apple, R.string.appledesc, R.string.applefertreq, (listOf("Compost", "Bone Meal","Worm Castings")), (listOf("Fish Emulsion", "Seaweed Extract", "Compost Tea"))),
    CropData("muskmelon", R.drawable.muskmelon, R.string.muskmelon, R.string.muskmelondesc, R.string.muskmelonfertreq, (listOf("Compost","Bone Meal", "Blood Meal", "Worm Castings")), (listOf( "Compost Tea", "Fish Emulsion", "Kelp Meal", "Well-aged Manure")))
)

fun getImageResIdForCrop(cropName: String): Int? {
    val normalizedCropName = cropName.lowercase()
    val cropData = CropList.find { it.name.lowercase() == normalizedCropName }

    return cropData?.imageResId // Return the imageResId or null if not found
}

fun getHeadResIdForCrop(cropName: String): Int? {
    val normalizedCropName = cropName.lowercase()
    val cropData = CropList.find { it.name.lowercase() == normalizedCropName }

    return cropData?.headResId // Return the imageResId or null if not found
}

fun getDescriptionIdForCrop(cropName: String): Int? {
    val normalizedCropName = cropName.lowercase()
    val cropData = CropList.find { it.name.lowercase() == normalizedCropName }

    return cropData?.descriptionResId // Return the imageResId or null if not found
}
fun getFertIdForCrop(cropName: String): Int? {
    val normalizedCropName = cropName.lowercase()
    val cropData = CropList.find { it.name.lowercase() == normalizedCropName }

    return cropData?.fertReqId // Return the imageResId or null if not found
}

fun getBeforePlantingForCrop(cropName: String): List<String>? {
    val normalizedCropName = cropName.lowercase()
    val cropData = CropList.find { it.name.lowercase() == normalizedCropName }

    return cropData?.beforePlanting
}

fun getAfterPlantingForCrop(cropName: String): List<String>? {
    val normalizedCropName = cropName.lowercase()
    val cropData = CropList.find { it.name.lowercase() == normalizedCropName }

    return cropData?.afterPlanting
}

@Composable
fun CropBox(crop:String) {
    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .align(Alignment.Center) // Align the card to the center
                .fillMaxSize() // Make the card take full width
        ) {
            val imageResId = getImageResIdForCrop(crop) ?: R.drawable.mothbeans
            val cropHeadId = getHeadResIdForCrop(crop) ?: R.string.mothbeans
            val cropDescId = getDescriptionIdForCrop(crop) ?: R.string.mothbeansdesc

            Column { // Wrap elements in a Column
                Image(painter = painterResource(id = imageResId), contentDescription = "CropImage")
                CardHeading(value = stringResource(cropHeadId))
                CardDesc(value = stringResource(cropDescId))

            }
        }
    }
}
@Composable
fun Box2(crop: String){
    Box(
        modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .align(Alignment.Center) // Align the card to the center
                .fillMaxSize()
        // Make the card take full width
        ) {
            val imageResId = getImageResIdForCrop(crop) ?: R.drawable.mothbeans
            val cropHeadId = getHeadResIdForCrop(crop) ?: R.string.mothbeans
            val cropFertReqId = getFertIdForCrop(crop) ?: R.string.mothbeansfertreq

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) { // Wrap elements in a Column
                Image(painter = painterResource(id = imageResId), contentDescription = "CropImage")
                CardHeading(value = stringResource(cropHeadId))
                CardSubHeading(value = "Fertilizer Requirements:")
                CardDesc2(value = stringResource(cropFertReqId))
                CardSubHeading(value = "Organic Fertilizers to Use:")
                CardSubHeading2(value = "Before Planting:")
                getBeforePlantingForCrop(crop)?.let { CircularBulletList(items = it) }
                CardSubHeading2(value = "After Planting:")
                getAfterPlantingForCrop(crop)?.let { CircularBulletList(items = it) }


            }
        }
    }
}

@Composable
fun PagerIndicator(pageCount: Int, pageState: PagerState) { // Add PagerState
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->

            val isSelected = index == pageState.currentPage // Check if current page

            val shape = if (isSelected) {
                RoundedCornerShape(8.dp) // Shape for the current page (rectangle)
            } else {
                CircleShape // Shape for other pages
            }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(10.dp)
                    .clip(shape) // Use the dynamic shape
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.inverseSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    ) // Adjust alpha as needed
            )
        }
    }
}

@Composable
fun AdvancedCropCarousel(crop: String) {
    var currentCard by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { when (currentCard) {
        0, 1 -> 2
        else -> 0
    }} )
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                currentCard = page
                pagerState.animateScrollToPage(page)
            }
    }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page -> AnimatedVisibility(
            visible = page == currentCard,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        )
            {
                when (currentCard) {
                    0 -> CropBox(crop)
                    1 -> Box2(crop)
//              2 -> CropStatsCard(cropSummary!!)
                }
            }
        }

        PagerIndicator(pageCount = 2, pagerState)
    }
}
