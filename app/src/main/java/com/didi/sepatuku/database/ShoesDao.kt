package com.didi.sepatuku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoesDao {
    @Query("SELECT * FROM Shoes ORDER BY id ASC")
    fun getAll(): LiveData<List<Shoes>>

    @Query("SELECT * FROM Shoes WHERE id IN (:shoesIds)")
    fun loadAllById(shoesIds: IntArray): List<Shoes>

    @Query("SELECT * FROM Shoes WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Shoes

    @Insert
    fun insertAll(vararg shoes: Shoes)

    @Delete
    fun delete(shoes: Shoes)
}