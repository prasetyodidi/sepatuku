package com.didi.sepatuku.presentation.shoe_favorite

import androidx.lifecycle.ViewModel
import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.use_case.FavoriteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

data class FavoriteUIState(
    val isLoading: Boolean = false,
    val items: List<Shoe> = emptyList(),
    val message: String? = null
)

class FavoriteViewModel constructor(
    favoriteUseCase: FavoriteUseCase
): ViewModel(){
    private var _state: MutableStateFlow<FavoriteUIState> = MutableStateFlow(FavoriteUIState())
    val state: StateFlow<FavoriteUIState> = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        favoriteUseCase.getFavorites()
            .onEach { event ->
                when(event){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            items = event.data ?: emptyList()
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            items = event.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            message = event.message ?: "unknown error"
                        )
                    }
                }
            }.launchIn(scope)
    }

}