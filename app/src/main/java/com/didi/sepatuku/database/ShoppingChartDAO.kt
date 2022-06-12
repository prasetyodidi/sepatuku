package com.didi.sepatuku.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingChartDAO {
    @Query("SELECT * FROM ShoppingChart")
    fun getAll(): List<ShoppingChart>

    @Insert
    fun insert(shoppingChart: ShoppingChart)

    @Delete
    fun delete(shoppingChart: ShoppingChart)

    @Update
    fun update(shoppingChart: ShoppingChart)

    @Query(
        "SELECT * FROM ShoppingChart WHERE name LIKE :name"
    )
    fun checkTypeOfNameDifferent(name: String): List<ShoppingChart>

}