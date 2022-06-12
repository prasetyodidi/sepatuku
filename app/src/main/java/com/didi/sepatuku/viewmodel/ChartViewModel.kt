package com.didi.sepatuku.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.didi.sepatuku.database.ShoppingChart
import com.didi.sepatuku.database.ShoppingChartDAO
import com.didi.sepatuku.database.ShoppingChartDatabase
import com.didi.sepatuku.repository.ShoppingChartRepository
import kotlinx.coroutines.*

class ChartViewModel(application: Application) : AndroidViewModel(application) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val repository: ShoppingChartRepository
    private val shoppingChartDAO: ShoppingChartDAO =
        ShoppingChartDatabase.getInstance(application).shoppingChartDAO()
    private var _listItems: MutableLiveData<List<ShoppingChart>> =
        MutableLiveData<List<ShoppingChart>>()
    val listItems: LiveData<List<ShoppingChart>> get() = _listItems

    init {
        repository = ShoppingChartRepository(shoppingChartDAO)
        setListItem()
    }

    private fun setListItem() {
        scope.launch {
            val list = async { repository.getAll() }
            withContext(Dispatchers.Main) {
                _listItems.value = list.await()
            }
        }
    }

    fun insert(shoppingChart: ShoppingChart){
        scope.launch {
            repository.insert(shoppingChart)
        }
    }

    fun delete(shoppingChart: ShoppingChart) {
        scope.launch {
            repository.delete(shoppingChart)
        }
    }
}