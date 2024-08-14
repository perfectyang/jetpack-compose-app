package com.perfectyang.bookshop.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, BankEntity::class], version = 1, exportSchema = false)
abstract class BankDB: RoomDatabase() {

    abstract fun userDao(): UserDAO

    abstract fun bankDao(): BankDAO

    companion object {
        var INSTANCE: BankDB? = null

        fun getInstace(context: Context): BankDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BankDB::class.java,
                        "bank_db"
                    ).build()
                }
                return instance
            }
        }
    }

}