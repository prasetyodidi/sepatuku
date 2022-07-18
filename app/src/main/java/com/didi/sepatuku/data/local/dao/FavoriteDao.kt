package com.didi.sepatuku.data.local.dao

import androidx.room.*
import com.didi.sepatuku.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavoriteEntity)

    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorites where name = :name")
    suspend fun deleteByName(name: String)
}