package com.didi.sepatuku.data.local

import androidx.room.Database
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
    }
}
