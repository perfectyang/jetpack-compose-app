package com.perfectyang.bookshop.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDAO {
    @Insert
    suspend fun addBank(bank: BankEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBank(bank: BankEntity)

    @Query("Delete FROM BankEntity where id = :id")
    suspend fun deleteBank(id : Int)

    @Query("SELECT * FROM BankEntity where category = :category AND user_id = :userId")
    fun getAllBanks(category: String = "1", userId: Int = 1): Flow<List<BankEntity>>

    @Query("SELECT * FROM BankEntity where category = :category AND user_id = :userId AND bank_name LIKE :title")
    fun searchBank(title: String, category: String = "1", userId: Int = 1): Flow<List<BankEntity>>


}
