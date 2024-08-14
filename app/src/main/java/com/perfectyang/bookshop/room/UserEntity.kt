package com.perfectyang.bookshop.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    var username: String,
    var password: String,
)
