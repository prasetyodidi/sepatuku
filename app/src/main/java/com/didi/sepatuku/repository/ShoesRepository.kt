package com.didi.sepatuku.repository

import android.database.SQLException
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.database.ShoesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ShoesRepository(private val shoesDao: ShoesDao) {
    private val scope = CoroutineScope(Dispatchers.IO)
    fun insert(vararg shoes: Shoes) {
        scope.launch {
            try {
                shoesDao.insertAll(*shoes)
            } catch (e: SQLException) {
                withContext(Dispatchers.Main){

                }
                Timber.d(e)
            }
        }
    }

    fun findByName(name: String): Shoes {
        return shoesDao.findByName(name)
    }

    fun getAll(): LiveData<List<Shoes>> {
        return shoesDao.getAll()
    }
}