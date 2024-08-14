package com.perfectyang.bookshop.router

sealed class Screens(val route: String) {
    object ScreenAuthRoute: Screens("Auth")
    object ScreenLoginRoute: Screens("login")
    object ScreenRegisterRoute: Screens("register")

    object ScreenAppRoute: Screens("App")
    object ScreenMineRoute: Screens("Mine")
    object ScreenHomeRoute: Screens("Home")

    object ScreenAddEditRoute: Screens("addEditBank")
}
