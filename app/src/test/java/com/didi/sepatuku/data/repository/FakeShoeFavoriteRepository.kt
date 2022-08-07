package com.didi.sepatuku.data.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeShoeFavoriteRepository: ShoeFavoriteRepository {
    private val listItems = mutableListOf<Shoe>()

    override suspend fun insertNewFavorite(item: Shoe) {
        listItems.add(item)
    }

    override fun getAllFavorites(): Flow<Resource<List<Shoe>>> = flow {
        emit(
            Resource.Success(
                data = listItems
            )
        )
    }

    override suspend fun deleteFavoriteItemByName(name: String) {
        val byName = listItems.find { it.name == name }
        listItems.remove(byName)
    }
}