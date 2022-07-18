package com.didi.sepatuku.presentation.shoe

import androidx.lifecycle.*
import com.didi.sepatuku.core.util.Resource
import com.didi.sepatuku.domain.use_case.ShoeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShoeViewModel @Inject constructor(
    private val shoeUseCase: ShoeUseCase
) : ViewModel() {

    private var _stateFlow: MutableStateFlow<ShoeUIState> = MutableStateFlow(ShoeUIState())
    val stateFlow: StateFlow<ShoeUIState> = _stateFlow.asStateFlow()

    init {
        getShoe()
    }

    private fun getShoe() {
        shoeUseCase.getShoe()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        Timber.d("Loading ${result.data?.size}")
                        _stateFlow.value = stateFlow.value.copy(
                            shoeItems = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        Timber.d("success ${result.data?.size}")
                        _stateFlow.value = stateFlow.value.copy(
                            shoeItems = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        Timber.d("Error ${result.data?.size}")
                        _stateFlow.value = stateFlow.value.copy(
                            shoeItems = result.data ?: emptyList(),
                            isLoading = false,
                            message = result.message ?: "unknown error"
                        )
                        Timber.d("emit error ${result.message}")
                    }
                }
            }.launchIn(viewModelScope)
    }
}