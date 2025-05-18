package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel


import android.net.Uri
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.CreateEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.UpdateEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.DeleteEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneushipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneuship
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class EntrepreneurshipActionState {
    object Idle : EntrepreneurshipActionState()
    object Success : EntrepreneurshipActionState()
    data class Error(val message: String) : EntrepreneurshipActionState()
    object Loading : EntrepreneurshipActionState()
}

@HiltViewModel
class EntrepreneurshipViewModel @Inject constructor(
    private val createEntrepreneushipUseCase: CreateEntrepreneushipUseCase
) : ViewModel() {

    private val defaultCategoriesOptions = listOf(
        "Comida",
        "Electrónica",
        "Joyeria",
        "Decoración",
    )

    private val _uiState = MutableStateFlow(
        EntrepreneurshipUiState(
            categoryOptions = defaultCategoriesOptions,
            entrepreneurshipCategory = "0", // índice en string
            selectedCategory = defaultCategoriesOptions[0]
        )
    )
    val uiState: StateFlow<EntrepreneurshipUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipActionState>(EntrepreneurshipActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipActionState> = _actionState.asStateFlow()

    fun onCategorySelected(categoryName: String) {
        val index = defaultCategoriesOptions.indexOf(categoryName).takeIf { it >= 0 } ?: 0
        _uiState.value = _uiState.value.copy(
            selectedCategory = categoryName,
            entrepreneurshipCategory = index.toString()
        )
    }

    fun saveEntrepreneurship() {
        val state = _uiState.value

        if (state.entrepreneurshipName.isBlank() || state.entrepreneurshipCategory.isBlank()) {
            _actionState.value = EntrepreneurshipActionState.Error("Completa todos los campos obligatorios.")
            return
        }

        try {
            val entrepreneurship = Entrepreneurship(
                id = state.id?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
                name = state.entrepreneurshipName,
                slogan = state.entrepreneurshipSlogan,
                description = state.entrepreneurshipDescription,
                creationDate = state.entrepreneurshipDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime(),
                customization = state.entrepreneurshipCustomization?.let { UUID.fromString(it) },
                email = state.entrepreneurshipEmail,
                phone = state.entrepreneurshipPhone,
                subscription = UUID.fromString(state.entrepreneurshipSubscription),
                status = state.entrepreneurshipStatus,
                category = state.entrepreneurshipCategory.toIntOrNull() ?: 0,
                socialNetworks = state.entrepreneurshipSocialNetworks.toIntOrNull() ?: 0,
                userFounder = UUID.fromString(state.entrepreneurshipUserFounder),
                imageUrl = state.entrepreneurshipImageUri?.toString()
            )

            viewModelScope.launch {
                _actionState.value = EntrepreneurshipActionState.Loading
                try {
                    createEntrepreneushipUseCase(entrepreneurship)
                    _actionState.value = EntrepreneurshipActionState.Success
                } catch (e: Exception) {
                    _actionState.value = EntrepreneurshipActionState.Error("Error: ${e.message}")
                }
            }

        } catch (e: Exception) {
            _actionState.value = EntrepreneurshipActionState.Error("Error en los datos: ${e.message}")
        }
    }

    fun clearForm() {
        _uiState.value = EntrepreneurshipUiState(
            categoryOptions = defaultCategoriesOptions,
            entrepreneurshipCategory = "0",
            selectedCategory = defaultCategoriesOptions[0]
        )
    }
}
