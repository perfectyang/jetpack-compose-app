package com.perfectyang.bookshop

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.perfectyang.bookshop.repository.Repository
import com.perfectyang.bookshop.room.BankDB
import com.perfectyang.bookshop.router.RouterIndex
import com.perfectyang.bookshop.viewmodel.BankViewModel
import com.perfectyang.bookshop.viewmodel.UserViewModel


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = BankDB.getInstace(context)
    val repository = Repository(db)
    val userViewModel = UserViewModel(repository, context)
    val bankViewModel = BankViewModel(repository = repository, userViewModel = userViewModel)

    // 所有页面路由进口
    RouterIndex(userViewModel = userViewModel, bankViewModel = bankViewModel)
}