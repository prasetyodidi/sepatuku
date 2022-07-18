package com.didi.sepatuku.presentation.detail_shoe

import com.didi.sepatuku.domain.model.DetailShoe

data class DetailShoeUIState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val detailShoe: DetailShoe? = null,
    val isFavorite: Boolean = false
)
