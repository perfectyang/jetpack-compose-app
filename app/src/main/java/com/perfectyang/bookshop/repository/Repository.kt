package com.perfectyang.bookshop.repository

import com.perfectyang.bookshop.room.BankDB
import com.perfectyang.bookshop.room.BankEntity
import com.perfectyang.bookshop.room.UserEntity

class Repository(val bankDB: BankDB) {

    // 用户类型
    fun getAllUsers() = bankDB.userDao().getAllUser()
    fun searchUser(username: String) = bankDB.userDao().searchUser(username)
    suspend fun register(user: UserEntity): Long {
        return bankDB.userDao().register(user)
    }
    suspend fun login(user: UserEntity): UserEntity {
        return bankDB.userDao().login(user.username, user.password)
    }


    // 银行信息方法操作
    fun getAllBanks(category: String, userId: Int) = bankDB.bankDao().getAllBanks(category, userId)
    suspend fun addBank(bank: BankEntity) {
        bankDB.bankDao().addBank(bank)
    }

    suspend fun deleteBank(id: Int) {
        bankDB.bankDao().deleteBank(id)
    }

    suspend fun updateBank(bank: BankEntity) {
        bankDB.bankDao().updateBank(bank)
    }

    fun searchBank(title: String, category: String, userId: Int) = bankDB.bankDao().searchBank("%$title%", category, userId)








}