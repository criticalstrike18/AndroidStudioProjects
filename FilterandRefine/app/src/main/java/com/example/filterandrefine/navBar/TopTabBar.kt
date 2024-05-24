package com.example.filterandrefine.navBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.filterandrefine.navigation.ScreenRoute
import com.example.filterandrefine.data.topTabItem
import com.example.filterandrefine.navigation.TopBarNavigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopTabBar(navControllerOg: NavHostController){
    val navController = rememberNavController()
    val tabItems = listOf(
        topTabItem(
            title = "Personal",
            route = ScreenRoute.PersonalScreen.route,
        ),
        topTabItem(
            title = "Services",
            route = ScreenRoute.ServicesScreen.route,
        ),
        topTabItem(
            title = "Businesses",
            route = ScreenRoute.BusinessesScreen.route,
        )
    )
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState{tabItems.size}
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(pagerState.currentPage) {
        navController.navigate(tabItems[pagerState.currentPage].route)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = item.title) }
                )
            }
        }

        HorizontalPager(state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
            TopBarNavigation(navController,navControllerOg)

        }

    }
}