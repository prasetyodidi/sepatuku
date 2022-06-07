package com.didi.sepatuku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Shoes::class], version = 1)
abstract class ShoesDatabase : RoomDatabase() {
    abstract fun shoesDao(): ShoesDao

    companion object {
        private const val EXTRA_TABLE_NAME = "shoes_database"

        @Volatile
        private var INSTANCE: ShoesDatabase? = null

        fun getInstance(context: Context): ShoesDatabase {
            if (INSTANCE == null) {
                synchronized(ShoesDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShoesDatabase::class.java, EXTRA_TABLE_NAME
                    ).build()

                }
            }
            return INSTANCE as ShoesDatabase
        }
    }
}