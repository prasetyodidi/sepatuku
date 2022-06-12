package com.didi.sepatuku.repository

import com.didi.sepatuku.database.ShoppingChart
import com.didi.sepatuku.database.ShoppingChartDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingChartRepository(private val shoppingChartDAO: ShoppingChartDAO) {
    private val scope = CoroutineScope(Dispatchers.IO)
    fun insert(shoppingChart: ShoppingChart) {
        scope.launch {
            val checkNameExist =
                shoppingChartDAO.checkTypeOfNameDifferent(shoppingChart.name as String)
            if (checkNameExist.isNotEmpty()) {
                for (item in checkNameExist) {
                    if (item.type.equals(shoppingChart.type)) {
                        shoppingChart.amount += item.amount
                        shoppingChartDAO.update(shoppingChart)
                    } else {
                        shoppingChartDAO.insert(shoppingChart)
                    }
                }
            } else {
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