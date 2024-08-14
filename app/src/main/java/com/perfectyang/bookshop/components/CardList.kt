package com.perfectyang.bookshop.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.perfectyang.bookshop.R
import com.perfectyang.bookshop.repository.Repository
import com.perfectyang.bookshop.room.BankDB
import com.perfectyang.bookshop.room.BankEntity
import com.perfectyang.bookshop.viewmodel.BankViewModel
import com.perfectyang.bookshop.viewmodel.UserViewModel
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.perfectyang.bookshop.router.Screens
import com.perfectyang.bookshop.types.Router
import kotlinx.coroutines.runBlocking

@Composable
fun CardList(category: String, navController: NavController) {
    val context = LocalContext.current
    val db = BankDB.getInstace(context)
    val repository = Repository(db)
    val userViewModel = UserViewModel(repository, context)
    val viewModel = BankViewModel(repository, category, userViewModel)
    val bankList by viewModel.banksList.observeAsState()

//    val coroutineScope = rememberCoroutineScope()


    val openDialog = remember { mutableStateOf(false) }

    var deletId by remember { mutableStateOf(0) }

    var inputText by remember {
        mutableStateOf("")
    }
    runBlocking {
        viewModel.searchBank(inputText)
    }

//    LaunchedEffect(category, openDialog.value, inputText) {
//       viewModel.searchBank(inputText)
//    }





    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            text = { Text(text = "是否要删除该数据???") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteBank(deletId, title = inputText)
                    openDialog.value = false
                }) { Text("确定") }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) { Text("取消") }
            }
        )
    }



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
                    .weight(1f),
                singleLine = true,
                value = inputText, onValueChange = {
                    inputText = it
                },
            )

            Button(
                modifier = Modifier.padding(start = 10.dp),
                onClick = {
                    viewModel.searchBank(inputText.trim())
                }) {
                Text("搜索")
            }
        }
        if (bankList?.isEmpty() == true) {
            EmptyTip()
        } else {
            bankList?.let{
                LazyColumn {
                    items(it) { bank ->
                        BankCard(bank, update = {
                            navController.navigate(Screens.ScreenAddEditRoute.route)
                        }) {
                            openDialog.value = true
                            deletId = bank.id
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BankCard(bank: BankEntity, update: () -> Unit = {}, callback: () -> Unit) {
   Card (
       modifier = Modifier
           .padding(10.dp)
           .fillMaxWidth()
           .clickable {
               update()
           }
   ) {
      Column (
          modifier = Modifier
              .padding(10.dp)
              .fillMaxSize()
      ) {
          Box(modifier = Modifier
              .fillMaxWidth()
          ) {
              Text(
                  modifier = Modifier
                      .align(Alignment.TopEnd)
                      .offset(y = -6.dp),
                  text =  if (bank.category == "1") "信用卡" else "借记卡",
                  color = Color.Red,
                  fontSize = 12.sp
              )
              Column(
                  modifier = Modifier
                      .fillMaxWidth()
                      .align(Alignment.Center)
              ) {
//                  Text(text = "用户id: ${bank.user_id}")
                  TextBlock(text = "银行名称", value = bank.bank_name)
                  TextBlock(text = "卡号", value = bank.bank_number)
                  if (bank.category == "1") {
                      TextBlock(text = "有效期", value = bank.valid_time)
                      TextBlock(text = "卡后三位", value = bank.back_card_three)
                      TextBlock(text = "卡额度", value = bank.quota)
                  }
              }
              IconButton(
                  modifier = Modifier
                      .align(Alignment.BottomEnd)
                      .offset(x = 16.dp, y = 12.dp),
                  onClick = {
                 callback()
              }) {
                  Icon(painter = painterResource(id = R.drawable.baseline_delete_outline_24), contentDescription = "delete")
              }

          }
      }
   }
}

@Composable
fun TextBlock(text: String, value: String) {
   Row (
       modifier = Modifier
           .fillMaxWidth()
           .padding(bottom = 4.dp)
   ) {
       Text(
           modifier = Modifier.width(80.dp),
           text = "$text:",
           fontSize = 16.sp
       )
       Text(
           modifier = Modifier.weight(1f),
           text = value,
           fontSize = 12.sp
       )
   }
}

