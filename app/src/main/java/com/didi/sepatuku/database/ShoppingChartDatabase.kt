package com.didi.sepatuku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingChart::class], version = 1)
abstract class ShoppingChartDatabase : RoomDatabase() {
    abstract fun shoppingChartDAO(): ShoppingChartDAO

    companion object {
        private const val EXTRA_TABLE_NAME = "shopping_chart_database"

        @Volatile
        private var INSTANCE: ShoppingChartDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): ShoppingChartDatabase {
            if (INSTANCE == null) {
                synchronized(ShoppingChartDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingChartDatabase::class.java,
                        EXTRA_TABLE_NAME
                    ).build()
                }
            }
            return INSTANCE as ShoppingChartDatabase
        }
    }
}