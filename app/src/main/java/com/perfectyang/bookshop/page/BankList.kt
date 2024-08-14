package com.perfectyang.bookshop.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavController
import com.perfectyang.bookshop.R
import com.perfectyang.bookshop.components.CardList
import com.perfectyang.bookshop.components.UserList
import com.perfectyang.bookshop.router.Screens
import com.perfectyang.bookshop.types.Router
import com.perfectyang.bookshop.ui.theme.Pink80
import com.perfectyang.bookshop.ui.theme.primaryColor
import com.perfectyang.bookshop.ui.theme.secondPrimaryColor
import com.perfectyang.bookshop.ui.theme.tabBackground


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BankList(modifier: Modifier = Modifier, navController: NavController) {

    var selectedTabIndex by remember{
        mutableStateOf(0)
    }
    val tabs = listOf(
        "信用卡",
        "借记卡",
    )
//    val pagerState = rememberPagerState { tabs.size }

//    LaunchedEffect(selectedTabIndex) {
//        pagerState.animateScrollToPage(selectedTabIndex)
//    }
//    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
//        if (!pagerState.isScrollInProgress) {
//            selectedTabIndex = pagerState.currentPage
//        }
//    }

    Box (
        modifier = modifier.fillMaxSize()
    ) {
        Box (
            modifier = Modifier.fillMaxSize()
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = primaryColor,
                    contentColor = tabBackground
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
                CardList((selectedTabIndex + 1).toString(), navController)

//                HorizontalPager(
//                    state = pagerState,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                ) { index ->
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        if (index == 0) {
//                        } else {
//                            UserList()
//                        }
//                    }
//
//                }
            }
        }

        Box (
            modifier = Modifier
                .size(50.dp)
                .background(Color.Transparent)
                .align(Alignment.BottomEnd)
                .offset(x = -10.dp, y = -30.dp)
        ) {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.ScreenAddEditRoute.route)
            }) {
                Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = "bank Add")
            }
        }

    }




}