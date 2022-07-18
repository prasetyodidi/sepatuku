package com.didi.sepatuku.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.didi.sepatuku.data.local.dao.FavoriteDao
import com.didi.sepatuku.data.local.dao.ShoesDao
import com.didi.sepatuku.data.local.dao.ShoppingCartDao
import com.didi.sepatuku.data.local.entity.FavoriteEntity
import com.didi.sepatuku.data.local.entity.ShoeEntity
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.data.local.entity.SizeEntity

@Database(
    entities = [
        ShoeEntity::class,
        SizeEntity::class,
        FavoriteEntity::class,
        ShoppingCartEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoeDao(): ShoesDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {

        const val DATABASE_NAME = "shoe_db"

        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}

private const val DATABASE_NAME = "sepatuku_db"
