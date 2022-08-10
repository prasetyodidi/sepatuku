package com.didi.sepatuku.presentation.detail_shoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.data.local.entity.ShoppingCartEntity
import com.didi.sepatuku.domain.model.Shoe
import com.didi.sepatuku.domain.use_case.FavoriteUseCase
import com.didi.sepatuku.domain.use_case.ShoeUseCase
import com.didi.sepatuku.domain.use_case.ShoppingCartUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.sql.SQLException

class DetailShoeViewModel constructor(
    private val shoeUseCase: ShoeUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val shoppingCartUseCase: ShoppingCartUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<DetailShoeUIState> = MutableStateFlow(DetailShoeUIState())
    val state: StateFlow<DetailShoeUIState> = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)
    private var searchJob: Job? = null

    fun getDetailShoe(name: String) {
        searchJob?.cancel()
        searchJob = scope.launch() {
            delay(500)
            shoeUseCase.getDetailShoe(name)
                .onEach { event ->
                    when (event) {
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = true,
                                detailShoe = event.data
                            )
                        }
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                isLoading = false,
                                detailShoe = event.data
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                isLoading = false,
                                detailShoe = event.data,
                                message = event.message
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    fun changeFavorite(){
        val value = !state.value.isFavorite
        _state.value = state.value.copy(
            isFavorite = value
        )
        if (value){
            state.value.detailShoe?.let {
                viewModelScope.launch {
                    insertFavorite(it.intoShoe())
                }
            }
        } else {
            state.value.detailShoe?.let {
                viewModelScope.launch {
                    deleteFavorite(it.name)
                }
            }
        }
    }

    private suspend fun insertFavorite(item: Shoe) {
        try {
            favoriteUseCase.insertFavorite(item)
        } catch (e: SQLException) {
            _state.value = state.value.copy(
                message = e.message
            )
        }
    }

    private suspend fun deleteFavorite(name: String) {
        try {
            favoriteUseCase.deleteFavorite(name)
        } catch (e: SQLException) {
            _state.value = state.value.copy(
                message = e.message
            )
        }
    }

    fun insertShoppingCart(item: ShoppingCartEntity) {
        viewModelScope.launch {
            try {
                shoppingCartUseCase.insertShoppingCartItem(item)
            } catch (e: SQLException) {
                _state.value = state.value.copy(
                    message = e.message
                )
            }
        }
    }
}

