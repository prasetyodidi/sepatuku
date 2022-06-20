package com.didi.sepatuku.repository

import com.didi.sepatuku.database.ShoesDao
import com.didi.sepatuku.model.Shoe
import com.didi.sepatuku.networking.ApiShoe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ShoeRepository private constructor(
    private val shoesDao: ShoesDao,
    private val apiShoe: ApiShoe,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    val shoes: Flow<List<Shoe>>
        get() = shoesDao.getAll()

    private suspend fun shouldUpdateShoeCache(): Boolean{
        return true
    }

    suspend fun tryUpdateRecentShoeCache(){
        if (shouldUpdateShoeCache()) fetchShoe()
    }

    private suspend fun fetchShoe(){
        val shoes = apiShoe.allShoes()
        shoesDao.insertAll(shoes)
    }

    companion object {
        @Volatile private var instance: ShoeRepository? = null

        fun getInstance(shoesDao: ShoesDao, apiShoe: ApiShoe) =
            instance ?: synchronized(this){
                instance ?: ShoeRepository(shoesDao, apiShoe).also { instance = it }
            }
    }
}