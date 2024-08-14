package com.perfectyang.bookshop.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.perfectyang.bookshop.R
import com.perfectyang.bookshop.room.UserEntity
import com.perfectyang.bookshop.router.Screens
import com.perfectyang.bookshop.types.Router
import com.perfectyang.bookshop.viewmodel.UserViewModel
import kotlinx.coroutines.launch


@Composable
fun Login(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val state = viewModel.userState.value
    var isRegister by remember {
        mutableStateOf(false)
    }
    var passwordVisibility by remember { mutableStateOf(false) }

   Column(
       modifier=Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center

   ) {
       Text(
           modifier = Modifier.padding(bottom = 50.dp),
           text = if(isRegister) "用户注册" else "用户登录",
       )
       OutlinedTextField(
           value = state.username,
           onValueChange = {
            viewModel.updateUser(it, state.password)
           },
           leadingIcon={
               Icon(painter = painterResource(id = R.drawable.baseline_account_circle_24) , contentDescription = "username")
           },
           trailingIcon={
               if (state.username.isNotEmpty()) {
                   Icon(painter = painterResource(id = R.drawable.baseline_close_24), contentDescription = "close", modifier = Modifier.clickable {
                       viewModel.updateUser("", state.password)
                   })
               } else {
                   null
               }
           },
           singleLine = true,
           modifier = Modifier
               .padding(start = 20.dp, end = 20.dp)
               .fillMaxWidth(),

       )
       Spacer(modifier = Modifier.height(30.dp))
       OutlinedTextField(
           value = state.password,
           onValueChange = {
                viewModel.updateUser(state.username, it)
            },

           leadingIcon={
               Icon(painter = painterResource(id = R.drawable.baseline_wifi_password_24) , contentDescription = "password")
           },
           trailingIcon = {
               IconButton(onClick = {
                   passwordVisibility = !passwordVisibility
               }) {
                   if (state.password.isNotEmpty()) {
                       Icon(
                           painter = painterResource(id = if (!passwordVisibility)  R.drawable.baseline_remove_red_eye_24 else R.drawable.close_eye),
                           contentDescription = "Toggle password visibility"
                       )
                   } else {
                       null
                   }
               }
           },
           visualTransformation= if (!passwordVisibility)  PasswordVisualTransformation() else  VisualTransformation.None,
           keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
           singleLine = true,
           modifier = Modifier
               .padding(start = 20.dp, end = 20.dp)
               .fillMaxWidth()

       )

       Button(
           modifier = Modifier
               .fillMaxWidth()
               .padding(top = 30.dp, start = 20.dp, end = 20.dp),
           onClick = {
               if (isRegister) {
                   viewModel.register(UserEntity(username = state.username, password = state.password), callback = {
                       coroutineScope.launch {
                           Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show()
                       }
                       isRegister = false
                   })
               } else {
                   viewModel.login(UserEntity(username = state.username, password = state.password), callback = { bool ->
                       if (bool) {
                           navController.navigate(Screens.ScreenAppRoute.route){
                               popUpTo(Screens.ScreenAuthRoute.route){
                                   inclusive = true
                               }
                           }
                       } else {
                           coroutineScope.launch {
                               Toast.makeText(context, "账号密码错误", Toast.LENGTH_SHORT).show()
                           }
                       }
                   })
               }
           }) {
           Text(text = if (isRegister)  "注册" else "登录")
       }

       TextButton(
           modifier = Modifier.padding(top = 30.dp),
           onClick = {
           isRegister = !isRegister
       }) {
           Text(text = if (!isRegister)  "注册 ?" else "登录")
       }

   }
}
















