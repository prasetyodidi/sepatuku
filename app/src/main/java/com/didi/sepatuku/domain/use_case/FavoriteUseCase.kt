package com.didi.sepatuku.domain.use_case

data class FavoriteUseCase(
    val getFavorites: GetFavorites,
    val deleteFavorite: DeleteFavorite,
    val insertFavorite: InsertFavorite
)