package com.perfectyang.bookshop.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

   @Insert
   suspend fun register(user: UserEntity): Long

    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity where username = :username")
    fun searchUser(username: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity where username = :username and password = :password")
    suspend fun login(username: String, password: String): UserEntity

}