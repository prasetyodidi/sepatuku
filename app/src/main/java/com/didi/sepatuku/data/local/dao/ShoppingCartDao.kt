package com.didi.sepatuku.data.local.dao

import androidx.room.*
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shopping_cart")
    fun getAll(): Flow<List<ShoppingCartEntity>>

    @Query("SELECT * FROM shopping_cart WHERE id IN(:id)")
    suspend fun getById(id: Int): ShoppingCartEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingCartEntity: ShoppingCartEntity)

    @Delete
    suspend fun delete(shoppingCartEntity: ShoppingCartEntity)

    @Update
    suspend fun update(shoppingCartEntity: ShoppingCartEntity)

}