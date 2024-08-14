package com.perfectyang.bookshop.router

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.perfectyang.bookshop.viewmodel.BankViewModel
import com.perfectyang.bookshop.viewmodel.UserViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun RouterIndex(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    bankViewModel: BankViewModel
) {
    val navController = rememberNavController()

//    LaunchedEffect(Unit) {
//        val userId = userViewModel.getUserData().firstOrNull()
//        userId?.let {
//            navController.navigate(Screens.ScreenAppRoute.route)
//        }
//    }



    NavHost(navController = navController, startDestination = Screens.ScreenAuthRoute.route) {
        AuthGraph(
            modifier,
            navController = navController,
            userViewModel= userViewModel
        )
        AppGrap(
            modifier,
            navController = navController,
            userViewModel = userViewModel,
            bankViewModel = bankViewModel
        )
    }
}
