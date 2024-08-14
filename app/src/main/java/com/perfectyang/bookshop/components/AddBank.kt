package com.perfectyang.bookshop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.perfectyang.bookshop.repository.Repository
import com.perfectyang.bookshop.room.BankDB
import com.perfectyang.bookshop.room.BankEntity
import com.perfectyang.bookshop.ui.theme.primaryColor
import com.perfectyang.bookshop.viewmodel.BankViewModel
import com.perfectyang.bookshop.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class BankType (
    val type: String,
    val title: String
)


@Composable
fun AddBank(modifier: Modifier = Modifier, navController: NavController) {

    val context = LocalContext.current
    val db = BankDB.getInstace(context)
    val repository = Repository(db)

    val userViewModel = UserViewModel(repository, context)
    val bankViewModel = BankViewModel(repository, userViewModel = userViewModel)


    val coroutineScope = rememberCoroutineScope()
    val radioOpts = listOf(BankType("1", "信用卡"), BankType("2", "借记卡"))
    var bankType by remember {
        mutableStateOf("1")
    }
    var uiState by remember {
        mutableStateOf(BankEntity(
         user_id = 0,
         bank_name = "",
         bank_number = "",
         valid_time = "",
         back_card_three = "",
         category = "1",
         bill_date = "",
         pay_date = "",
         quota = ""
        ))
    }

    Column (
        modifier = modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(primaryColor)
            .padding(10.dp)
    ) {
        Row(
            Modifier
                .selectableGroup()
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                color = Color.White,
                text = "卡类型"
            )
            radioOpts.forEach { opt->
                Row (
                    modifier= Modifier
                        .height(50.dp)
                        .selectable(
                            selected = (opt.type === bankType),
                            onClick = {
                                bankType = opt.type
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (opt.type == bankType),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = opt.title,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        TextInputComp(
            text = "银行名称",
            value = uiState.bank_name
        ) {
            uiState = uiState.copy(
                bank_name = it
            )
        }
        TextInputComp(
            text = "银行卡号",
            value = uiState.bank_number
        ) {
            uiState = uiState.copy(
                bank_number = it
            )
        }
        if (bankType === "1") {
            TextInputComp(
                text = "有效期",
                value = uiState.valid_time
            ) {
                uiState = uiState.copy(
                    valid_time = it
                )
            }
            TextInputComp(
                text = "卡背面三位数",
                value = uiState.back_card_three
            ) {
                uiState = uiState.copy(
                    back_card_three = it
                )
            }
            TextInputComp(
                text = "账单日",
                value = uiState.bill_date
            ) {
                uiState = uiState.copy(
                    bill_date = it
                )
            }
            TextInputComp(
                text = "还款日",
                value = uiState.pay_date
            ) {
                uiState = uiState.copy(
                    pay_date = it
                )
            }
            TextInputComp(
                text = "总额度",
                value = uiState.quota
            ) {
                uiState = uiState.copy(
                    quota = it
                )
            }
        }


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                navController.popBackStack()
            }) {
                Text(text = "取消")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    coroutineScope.launch {
                        val userId = userViewModel.getUserData().firstOrNull()
                        uiState = userId.let {
                            uiState.copy(
                                category = bankType,
                                user_id = it  ?: 0
                            )
                        }
                        if (bankType == "2") {
                            uiState = uiState.copy(
                                valid_time = "",
                                back_card_three = "",
                                bill_date = "",
                                pay_date = "",
                                quota = ""
                            )
                        }
                        bankViewModel.addBank(uiState) {
                            navController.popBackStack()
                        }
                    }
                }
            ) {
                Text(text = "保存")
            }
        }

    }
}

@Composable
fun TextInputComp(text: String, value: String, callback: (text: String)->Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.width(100.dp),
            text = text)
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp, end = 10.dp)
                .defaultMinSize(minHeight = 20.dp)
            ,
            value = value,
            onValueChange = {
                callback(it)
            },
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp
            )
        )
    }
}

