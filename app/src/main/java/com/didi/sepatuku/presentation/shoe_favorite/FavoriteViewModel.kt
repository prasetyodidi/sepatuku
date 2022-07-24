package com.didi.sepatuku.presentation.shoe_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.use_case.FavoriteUseCase
import kotlinx.coroutines.flow.*
import timber.log.Timber

data class FavoriteUIState(
    val isLoading: Boolean = false,
    val items: List<Shoe> = emptyList(),
    val message: String? = null
)

class FavoriteViewModel constructor(
    private val favoriteUseCase: FavoriteUseCase
): ViewModel(){
    private var _state: MutableStateFlow<FavoriteUIState> = MutableStateFlow(FavoriteUIState())
    val state: StateFlow<FavoriteUIState> = _state.asStateFlow()

    init {
        Timber.d("fav view model")
        favoriteUseCase.getFavorites()
            .onEach { event ->
                when(event){
                    is Resource.Loading -> {
                        Timber.d("fav view model loading : ${event.data?.size}")
                        _state.value = state.value.copy(
                            isLoading = true,
                            items = event.data ?: emptyList()
                        )
                    }
                    is Resource.Success -> {
                        Timber.d("fav view model success : ${event.data?.size}")
                        _state.value = state.value.copy(
                            isLoading = false,
                            items = event.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        Timber.d("fav view model error : ${event.data?.size}")
                        _state.value = state.value.copy(
                            isLoading = false,
                            message = event.message ?: "unknown error"
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


//    var favoritesItems: LiveData<List<FavoriteEntity>> = favoriteRepository.favorites.asLiveData()
//    private fun launchDataLoad(scope: CoroutineScope, block: suspend () -> Unit): Job{
//        return scope.launch {
//            try {
//                _isLoading.value = true
//                block
//            } catch (e: Throwable) {
//                _snackbar.value = e.message
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

}