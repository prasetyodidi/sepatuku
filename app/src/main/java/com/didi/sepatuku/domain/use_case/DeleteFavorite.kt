package com.didi.sepatuku.domain.use_case

import com.didi.sepatuku.domain.repository.ShoeFavoriteRepository

class DeleteFavorite(
    private val repository: ShoeFavoriteRepository
) {
    suspend operator fun invoke(name: String){
        return repository.deleteFavoriteItemByName(name)
    }
}