package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavorites(
    private val repository: ShoeFavoriteRepository
) {
    operator fun invoke(): Flow<Resource<List<Shoe>>> {
        return repository.getAllFavorites()
    }
}