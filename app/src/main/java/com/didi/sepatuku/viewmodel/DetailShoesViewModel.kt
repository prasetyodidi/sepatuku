package com.didi.sepatuku.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.didi.sepatuku.database.Shoes
import com.didi.sepatuku.database.ShoesDao
import com.didi.sepatuku.database.ShoesDatabase
import com.didi.sepatuku.repository.ShoesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailShoesViewModel(application: Application) : AndroidViewModel(application) {
    private val crScope = CoroutineScope(Dispatchers.IO)
    private val repository: ShoesRepository
    private val shoesDao: ShoesDao
    private var _item: MutableLiveData<Shoes> = MutableLiveData<Shoes>()
    val item: LiveData<Shoes> = _item
    private var _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        shoesDao = ShoesDatabase.getInstance(application).shoesDao()
        repository = ShoesRepository(shoesDao)
    }

}