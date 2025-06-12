package com.codeoflegends.unimarket.features.home.ui.viewModel

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.home.data.useCase.GetBannersUseCase
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeActionState {
    object Idle : HomeActionState()
    object Success : HomeActionState()
    data class Error(val message: String) : HomeActionState()
    object Loading : HomeActionState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getBannersUseCase: GetBannersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<HomeActionState>(HomeActionState.Idle)
    val actionState: StateFlow<HomeActionState> = _actionState.asStateFlow()

    init {
        loadBanners()
    }

    fun loadBanners() {
        _actionState.value = HomeActionState.Loading
        viewModelScope.launch {
            getBannersUseCase().let { banners ->
                _uiState.value = _uiState.value.copy(banners = banners)
                _actionState.value = HomeActionState.Success
            }
        }
    }


    fun onNavigationItemSelected(route: String) {
        _uiState.value = _uiState.value.copy(currentRoute = route)
    }
}