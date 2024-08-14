package com.perfectyang.bookshop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun EmptyTip(modifier: Modifier = Modifier) {
   Row (
       modifier = Modifier.fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.Center
   ){
       Text("暂无数据")
   }
}