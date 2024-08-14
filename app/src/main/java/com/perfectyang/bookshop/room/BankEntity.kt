package com.perfectyang.bookshop.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BankEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var user_id: Int,
    var bank_name: String,
    var bank_number: String,
    var valid_time: String,
    var back_card_three: String,
    // 1 信用卡， 2 借记卡
    var category: String = "1",
    var bill_date: String,
    var pay_date: String,
    var quota: String,
)