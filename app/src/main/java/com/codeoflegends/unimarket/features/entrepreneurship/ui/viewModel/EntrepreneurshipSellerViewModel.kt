package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipSellerActionState {
    object Idle : EntrepreneurshipSellerActionState()
    object Success : EntrepreneurshipSellerActionState()
    data class Error(val message: String) : EntrepreneurshipSellerActionState()
    object Loading : EntrepreneurshipSellerActionState()
}

@HiltViewModel
class EntrepreneurshipSellerViewModel @Inject constructor(
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase
) : ViewModel() {
    // UI states
    private val _entrepreneurshipUiState = MutableStateFlow(EntrepreneurshipBasicUiState())
    val entrepreneurshipUiState: StateFlow<EntrepreneurshipBasicUiState> = _entrepreneurshipUiState.asStateFlow()

    private val _productsUiState = MutableStateFlow(EntrepreneurshipProductsPreviewUiState())
    val productsUiState: StateFlow<EntrepreneurshipProductsPreviewUiState> = _productsUiState.asStateFlow()

    private val _partnersUiState = MutableStateFlow(EntrepreneurshipPartnersUiState())
    val partnersUiState: StateFlow<EntrepreneurshipPartnersUiState> = _partnersUiState.asStateFlow()

    // Action states
    private val _uiState = MutableStateFlow(EntrepreneurshipBasicUiState())
    val uiState: StateFlow<EntrepreneurshipBasicUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipSellerActionState>(EntrepreneurshipSellerActionState.Loading)
    val actionState: StateFlow<EntrepreneurshipSellerActionState> = _actionState.asStateFlow()

    // Navigation state
    private val _currentRoute = MutableStateFlow("home")
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    fun onNavigationItemSelected(route: String) {
        _currentRoute.value = route
    }

    fun loadEntrepreneurship(entrepreneurshipId: UUID) {
        viewModelScope.launch {
            _actionState.value = EntrepreneurshipSellerActionState.Loading

            try {
                val entrepreneurship = getEntrepreneurshipUseCase(entrepreneurshipId)

                _entrepreneurshipUiState.value = _entrepreneurshipUiState.value.copy(
                    id = entrepreneurshipId,
                    name = entrepreneurship.name,
                    slogan = entrepreneurship.slogan,
                    description = entrepreneurship.description,
                    creationDate = entrepreneurship.creationDate,
                    customization = entrepreneurship.customization,
                    email = entrepreneurship.email,
                    phone = entrepreneurship.phone,
                    tags = entrepreneurship.tags
                )

                _uiState.value = _uiState.value.copy(
                    id = entrepreneurshipId,
                    name = entrepreneurship.name,
                    slogan = entrepreneurship.slogan,
                    description = entrepreneurship.description,
                    creationDate = entrepreneurship.creationDate,
                    customization = entrepreneurship.customization,
                    email = entrepreneurship.email,
                    phone = entrepreneurship.phone,
                    tags = entrepreneurship.tags
                )

                _actionState.value = EntrepreneurshipSellerActionState.Success
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipSellerActionState.Error("Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }
}