package com.didi.sepatuku.data.repository

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.data.local.dao.FavoriteDao
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.sql.SQLException

class ShoeFavoriteRepositoryImpl(
    private val dao: FavoriteDao
): ShoeFavoriteRepository {
    override suspend fun insertNewFavorite(item: Shoe) {
        try {
            dao.insert(item.intoFavoriteEntity())
        } catch (e: SQLException){
            Timber.d("error : ${e.message}")
        }
    }

    override fun getAllFavorites(): Flow<Resource<List<Shoe>>> = flow {
        emit(Resource.Loading())

        val favorites = dao.getAll()

        favorites
            .distinctUntilChanged()
            .collect { items ->
                emit(Resource.Success(items.map { it.intoShoe() }))

            }
    }

    override suspend fun deleteFavoriteItemByName(name: String) {
        dao.deleteByName(name)
    }
}