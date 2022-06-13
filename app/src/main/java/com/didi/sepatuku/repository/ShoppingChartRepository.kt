package com.didi.sepatuku.repository

import com.didi.sepatuku.database.ShoppingChart
import com.didi.sepatuku.database.ShoppingChartDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ShoppingChartRepository(private val shoppingChartDAO: ShoppingChartDAO) {
    private val scope = CoroutineScope(Dispatchers.IO)
    fun insert(shoppingChart: ShoppingChart) {
        scope.launch {
            val checkNameExist =
                shoppingChartDAO.checkTypeOfNameDifferent(shoppingChart.name as String)
            if (checkNameExist.isNotEmpty()) {
                Timber.d("name exist")
                for (item in checkNameExist) {
                    if (item.type.equals(shoppingChart.type)) {
                        Timber.d("name exist and type equals: add amount")
                        Timber.d("amount before: ${item.amount}")
                        shoppingChart.amount += item.amount
                        Timber.d("amount after: ${shoppingChart.amount}")
                        shoppingChartDAO.update(shoppingChart)
                    } else {
                        Timber.d("name exist but type not equals: insert")
                        shoppingChartDAO.insert(shoppingChart)
                    }
                }
            } else {
                Timber.d("name not exist: add item")
                shoppingChartDAO.insert(shoppingChart)
            }
        }
    }

    fun getAll(): List<ShoppingChart> = shoppingChartDAO.getAll()

    fun delete(shoppingChart: ShoppingChart) {
        scope.launch {
            shoppingChartDAO.delete(shoppingChart)
        }
    }
}