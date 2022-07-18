package com.didi.sepatuku.domain.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.Shoe
import kotlinx.coroutines.flow.Flow

interface ShoeFavoriteRepository {

    suspend fun insertNewFavorite(item: Shoe)

    fun getAllFavorites(): Flow<Resource<List<Shoe>>>

    suspend fun deleteFavoriteItemByName(name: String)
}