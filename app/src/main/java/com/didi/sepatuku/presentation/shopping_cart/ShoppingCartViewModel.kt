package com.didi.sepatuku.presentation.shopping_cart

import androidx.lifecycle.ViewModel
import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.model.CartItem
import com.didi.sepatuku.domain.use_case.ShoppingCartUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

data class ShoppingCartUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val items: List<CartItem> = emptyList()
)

class ShoppingCartViewModel constructor(
    private val shoppingCartUseCase: ShoppingCartUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<ShoppingCartUiState> =
        MutableStateFlow(ShoppingCartUiState())
    val state: StateFlow<ShoppingCartUiState> = _state.asStateFlow()

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        getData()
    }

    private fun getData(){
        shoppingCartUseCase.getShoppingCartItems()
            .onEach { event ->
                when (event) {
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

    fun deleteItemFromShoppingCart(item: CartItem){
        scope.launch {
            Timber.d("viewModel noDelete:  ${item.name}")
            shoppingCartUseCase.deleteShoppingCartItem(item)
            getData()
        }
    }

    fun updateAmount(item: CartItem, amount: Int){
        scope.launch {
            if (amount >= 1){
                Timber.d("viewModel onUpdate amount: ${item.amount} $amount")
                shoppingCartUseCase.updateShoppingCartItem(item.copy(amount = amount))
                getData()
            }
        }
    }
}