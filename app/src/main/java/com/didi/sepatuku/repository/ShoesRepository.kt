package com.didi.sepatuku.repository

import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.database.ShoesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoesRepository(private val shoesDao: ShoesDao) {
    private val scope = CoroutineScope(Dispatchers.IO)
    fun insert(vararg shoes: Shoes) {
        scope.launch {
            shoesDao.insertAll(*shoes)
        }
    }

    fun findByName(name: String): Shoes {
        return shoesDao.findByName(name)
    }

    fun getAll(): List<Shoes>{
        return shoesDao.getAll()
    }
}