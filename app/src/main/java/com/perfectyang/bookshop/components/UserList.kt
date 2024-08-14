package com.perfectyang.bookshop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.perfectyang.bookshop.repository.Repository
import com.perfectyang.bookshop.room.BankDB
import com.perfectyang.bookshop.room.UserEntity
import com.perfectyang.bookshop.viewmodel.UserViewModel

@Composable
fun UserList() {
    val context = LocalContext.current
    val db = BankDB.getInstace(context)
    val repository = Repository(db)
    val userViewModel = UserViewModel(repository, context)
    val userList by userViewModel.userList.collectAsState(initial = emptyList())




    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                singleLine = true,
                value = "" , onValueChange = {
                },
            )

            Button(
                modifier = Modifier.padding(start = 10.dp),
                onClick = {
                }) {
                Text("search")
            }
        }
        if (userList.isEmpty()) {
            EmptyTip()
        } else {
            LazyColumn {
                items(userList) { user ->
                    UserCard(user)
                }
            }
        }
    }
}

@Composable
fun UserCard(user: UserEntity) {
   Card (
       modifier = Modifier
           .padding(10.dp)
           .fillMaxWidth()
   ) {
      Column (
          modifier = Modifier.padding(10.dp)
      ) {
          Text(text = "用户id: ${user.userId}")
          Text(text = "用户账号: ${user.username}")
          Text(text = "用户密码: ${user.password}")
      }
   }
}

@Preview
@Composable
fun ViewPre () {
    val user = UserEntity(1, "若", "pass")
    UserCard(user = user)
}

