package com.perfectyang.bookshop.router

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.perfectyang.bookshop.page.Login
import com.perfectyang.bookshop.viewmodel.UserViewModel

fun NavGraphBuilder.AuthGraph(modifier: Modifier, navController: NavController,  userViewModel: UserViewModel) {
    // 这里是总入口路由, 子路由可以多级定义
    navigation(startDestination = Screens.ScreenLoginRoute.route, route = Screens.ScreenAuthRoute.route) {
        composable(Screens.ScreenLoginRoute.route) {
            Login(
                modifier,
                navController = navController,
                viewModel = userViewModel
            )
        }
    }
}
