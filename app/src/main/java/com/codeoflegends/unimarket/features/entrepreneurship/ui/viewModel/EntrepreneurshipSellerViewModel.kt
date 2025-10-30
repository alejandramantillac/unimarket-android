package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.UpdateEntrepreneurshipUseCase
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
    object Updating : EntrepreneurshipSellerActionState()
    object UpdateSuccess : EntrepreneurshipSellerActionState()
}

@HiltViewModel
class EntrepreneurshipSellerViewModel @Inject constructor(
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase,
    private val updateEntrepreneurshipUseCase: UpdateEntrepreneurshipUseCase
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

    // Edit state management
    private val _isEditDialogOpen = MutableStateFlow(false)
    val isEditDialogOpen: StateFlow<Boolean> = _isEditDialogOpen.asStateFlow()

    fun openEditDialog() {
        _isEditDialogOpen.value = true
    }

    fun closeEditDialog() {
        _isEditDialogOpen.value = false
    }

    fun updateEntrepreneurship(
        name: String,
        slogan: String,
        description: String,
        email: String,
        phone: String
    ) {
        viewModelScope.launch {
            _actionState.value = EntrepreneurshipSellerActionState.Updating

            try {
                val currentState = _entrepreneurshipUiState.value
                
                // Obtener el emprendimiento completo del backend para tener todos los campos
                val fullEntrepreneurship = getEntrepreneurshipUseCase(currentState.id)
                
                // Actualizar solo los campos editados
                val updatedEntrepreneurship = fullEntrepreneurship.copy(
                    name = name,
                    slogan = slogan,
                    description = description,
                    email = email,
                    phone = phone
                )

                updateEntrepreneurshipUseCase(updatedEntrepreneurship)

                _entrepreneurshipUiState.value = _entrepreneurshipUiState.value.copy(
                    name = name,
                    slogan = slogan,
                    description = description,
                    email = email,
                    phone = phone
                )

                _uiState.value = _uiState.value.copy(
                    name = name,
                    slogan = slogan,
                    description = description,
                    email = email,
                    phone = phone
                )

                _actionState.value = EntrepreneurshipSellerActionState.UpdateSuccess
                _isEditDialogOpen.value = false
                
                // Volver al estado Success después de 2 segundos
                kotlinx.coroutines.delay(2000)
                _actionState.value = EntrepreneurshipSellerActionState.Success
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipSellerActionState.Error("Error al actualizar el emprendimiento: ${e.message}")
                Log.e("EntrepreneurshipSellerViewModel", "Error updating entrepreneurship", e)
            }
        }
    }
}