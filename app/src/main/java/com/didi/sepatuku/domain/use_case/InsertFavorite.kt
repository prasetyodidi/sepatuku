package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository

class InsertFavorite(
    private val repository: ShoeFavoriteRepository
) {
    suspend operator fun invoke(item: Shoe){
        repository.insertNewFavorite(item)
    }
}