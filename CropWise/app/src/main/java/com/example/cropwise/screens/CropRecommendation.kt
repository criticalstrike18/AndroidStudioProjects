@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.cropwise.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
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
    CropData("cotton",R.drawable.cotton,R.string.cotton,R.string.cottondesc,R.string.cottonfertreq,(listOf("Compost","Manure","Bone Meal","Fish Meal")),(listOf("Seaweed Extract","Compost Tea","Fish Emulsion","Worm Castings"))),
)

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
            val cropImageMap = mapOf(
                "mothbeans" to R.drawable.mothbeans,
                "coffee" to R.drawable.coffee,
                "rice" to R.drawable.rice,
                "chickpea" to R.drawable.chickpea,
                "cotton" to R.drawable.cotton,
                "maize" to R.drawable.maize,
                "coconut" to R.drawable.coconut,
                "blackgram" to R.drawable.blackgram,
                "jute" to R.drawable.jute,
                "kidneybeans" to R.drawable.kidneybeans,
                "pigeonpeas" to R.drawable.pigeonspeas,
                "orange" to R.drawable.orange,
                "pomegranate" to R.drawable.pomegranate,
                "apple" to R.drawable.apple,
                "muskmelon" to R.drawable.muskmelon,
            )
            val cropHeading = mapOf(
                "mothbeans" to R.string.mothbeans,
                "coffee" to R.string.coffee,
                "rice" to R.string.rice,
                "chickpea" to R.string.chickpeas,
                "cotton" to R.string.cotton,
                "maize" to R.string.maize,
                "coconut" to R.string.coconut,
                "blackgram" to R.string.blackgram,
                "jute" to R.string.jute,
                "kidneybeans" to R.string.kidneybeans,
                "pigeonpeas" to R.string.pigeon_peas,
                "orange" to R.string.oranges,
                "pomegranate" to R.string.pomegranate,
                "muskmelon" to R.string.muskmelon,
            )
            val cropDesc = mapOf(
                "mothbeans" to R.string.mothbeansdesc,
                "coffee" to R.string.coffeedesc,
                "rice" to R.string.ricedesc,
                "chickpea" to R.string.chickpeadesc,
                "cotton" to R.string.cottondesc,
                "maize" to R.string.maizedesc,
                "coconut" to R.string.coconutdesc,
                "blackgram" to R.string.blackgramdesc,
                "jute" to R.string.jutedesc,
                "kidneybeans" to R.string.kidneybeansdesc,
                "pigeonpeas" to R.string.pigeonpeasdesc,
                "orange" to R.string.orangedesc,
                "pomegranate" to R.string.pomegranatedesc,
                "apple" to R.string.appledesc,
                "muskmelon" to R.string.muskmelondesc,
            )
            val imageResId = cropImageMap[crop.lowercase()] ?: R.drawable.mothbeans
            val cropHeadId = cropHeading[crop.lowercase()] ?: R.string.mothbeans
            val cropDescId = cropDesc[crop.lowercase()] ?: R.string.mothbeansdesc

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
            val cropImageMap = mapOf(
                "mothbeans" to R.drawable.mothbeans,
                "coffee" to R.drawable.coffee,
                "rice" to R.drawable.rice,
                "chickpea" to R.drawable.chickpea,
                "cotton" to R.drawable.cotton,
                "maize" to R.drawable.maize,
                "coconut" to R.drawable.coconut,
                "blackgram" to R.drawable.blackgram,
                "jute" to R.drawable.jute,
                "kidneybeans" to R.drawable.kidneybeans,
                "pigeonpeas" to R.drawable.pigeonspeas,
                "orange" to R.drawable.orange,
                "pomegranate" to R.drawable.pomegranate,
                "apple" to R.drawable.apple,
                "muskmelon" to R.drawable.muskmelon,
            )
            val cropHeading = mapOf(
                "mothbeans" to R.string.mothbeans,
                "coffee" to R.string.coffee,
                "rice" to R.string.rice,
                "chickpea" to R.string.chickpeas,
                "cotton" to R.string.cotton,
                "maize" to R.string.maize,
                "coconut" to R.string.coconut,
                "blackgram" to R.string.blackgram,
                "jute" to R.string.jute,
                "kidneybeans" to R.string.kidneybeans,
                "pigeonpeas" to R.string.pigeon_peas,
                "orange" to R.string.oranges,
                "pomegranate" to R.string.pomegranate,
                "muskmelon" to R.string.muskmelon,
            )
            val cropFertReq = mapOf(
                "mothbeans" to R.string.mothbeansdesc,
                "coffee" to R.string.coffeedesc,
                "rice" to R.string.ricedesc,
                "chickpea" to R.string.chickpeadesc,
                "cotton" to R.string.cottondesc,
                "maize" to R.string.maizedesc,
                "coconut" to R.string.coconutdesc,
                "blackgram" to R.string.blackgramdesc,
                "jute" to R.string.jutedesc,
                "kidneybeans" to R.string.kidneybeansdesc,
                "pigeonpeas" to R.string.pigeonpeasdesc,
                "orange" to R.string.orangedesc,
                "pomegranate" to R.string.pomegranatedesc,
                "apple" to R.string.appledesc,
                "muskmelon" to R.string.muskmelonfertreq,
            )
            val imageResId = cropImageMap[crop.lowercase()] ?: R.drawable.mothbeans
            val cropHeadId = cropHeading[crop.lowercase()] ?: R.string.mothbeans
            val cropFertReqId = cropFertReq[crop.lowercase()] ?: R.string.mothbeansdesc

            val beforePlantingFertilizers = listOf(
                "Compost",
                "Bone Meal",
                "Blood Meal",
                "Worm Castings"
            )

// After Planting
            val afterPlantingFertilizers = listOf(
                "Compost Tea",
                "Fish Emulsion",
                "Kelp Meal",
                "Well-aged Manure"
            )

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) { // Wrap elements in a Column
                Image(painter = painterResource(id = imageResId), contentDescription = "CropImage")
                CardHeading(value = stringResource(cropHeadId))
                CardSubHeading(value = "Fertilizer Requirements:")
                CardDesc2(value = stringResource(cropFertReqId))
                CardSubHeading(value = "Organic Fertilizers to Use:")
                CardSubHeading2(value = "Before Planting:")
                CircularBulletList(items = beforePlantingFertilizers)
                CardSubHeading2(value = "After Planting:")
                CircularBulletList(items = afterPlantingFertilizers)


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
        ) { _ ->
            when (currentCard) {
                0 -> CropBox(crop)
                1 -> Box2(crop)
//              2 -> CropStatsCard(cropSummary!!)
            }
        }

        PagerIndicator(pageCount = 2, pagerState)
    }
}
