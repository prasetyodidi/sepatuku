package com.didi.sepatuku.presentation.shoe

import com.didi.sepatuku.domain.model.Shoe

data class ShoeUIState(
    val shoeItems: List<Shoe> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = ""
)