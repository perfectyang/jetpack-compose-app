package com.perfectyang.bookshop.router

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.perfectyang.bookshop.components.AddBank
import com.perfectyang.bookshop.page.BankList
import com.perfectyang.bookshop.page.Mine
import com.perfectyang.bookshop.viewmodel.BankViewModel
import com.perfectyang.bookshop.viewmodel.UserViewModel

fun NavGraphBuilder.AppGrap(
    modifier: Modifier,
    navController: NavController,
    userViewModel: UserViewModel,
    bankViewModel: BankViewModel
) {
    navigation(startDestination = Screens.ScreenHomeRoute.route, route= Screens.ScreenAppRoute.route) {
        composable(Screens.ScreenHomeRoute.route) {
            BankList(navController = navController)
        }
        composable(Screens.ScreenMineRoute.route) {
            Mine(modifier, navController = navController)
        }
        composable(Screens.ScreenAddEditRoute.route) {
            AddBank(modifier, navController = navController)
        }
    }

}
