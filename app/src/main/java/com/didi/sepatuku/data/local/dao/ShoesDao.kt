package com.didi.sepatuku.data.local.dao

import androidx.room.*
import com.didi.sepatuku.data.local.entity.ShoeWithSize
import com.didi.sepatuku.data.local.entity.ShoeEntity
import com.didi.sepatuku.data.local.entity.SizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoesDao {

    @Query("SELECT * FROM shoes ORDER BY id ASC")
    fun getAllShoes(): Flow<List<ShoeEntity>>

    @Transaction
    @Query("SELECT * FROM shoes")
    suspend fun getAllDetailShoes(): List<ShoeWithSize>

    @Transaction
    @Query("SELECT * FROM shoes WHERE name LIKE :name LIMIT 1")
    suspend fun getDetailShoeByName(name: String): ShoeWithSize

    @Insert
    suspend fun insertShoe(shoe: ShoeEntity)
    @Insert
    suspend fun insertSizes(sizes: List<SizeEntity>)

    suspend fun insertDetailShoe(detailShoe: ShoeWithSize){
        insertShoe(detailShoe.shoe)
        insertSizes(detailShoe.sizes)
    }

    suspend fun insertAllDetailShoe(items: List<ShoeWithSize>){
        for (item in items){
            insertDetailShoe(item)
        }
    }

    @Query("DELETE FROM sizes WHERE idShoe IN(:idShoes)")
    suspend fun deleteSizes(idShoes: List<String>)

    @Query("DELETE FROM shoes WHERE id IN(:idShoes)")
    suspend fun deleteShoes(idShoes: List<String>)

    @Transaction
    suspend fun deleteShoesById(idShoes: List<String>){
        deleteShoes(idShoes)
        deleteSizes(idShoes)
    }

}

