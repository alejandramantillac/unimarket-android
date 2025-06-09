package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

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

sealed class EntrepreneurshipBasicActionState {
    object Idle : EntrepreneurshipBasicActionState()
    object Success : EntrepreneurshipBasicActionState()
    data class Error(val message: String) : EntrepreneurshipBasicActionState()
    object Loading : EntrepreneurshipBasicActionState()
}

@HiltViewModel
class EntrepreneurshipBasicViewModel @Inject constructor(
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntrepreneurshipBasicUiState())
    val uiState: StateFlow<EntrepreneurshipBasicUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipBasicActionState>(EntrepreneurshipBasicActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipBasicActionState> = _actionState.asStateFlow()

    fun onNavigationItemSelected(route: String) {}

    private fun validateAndGetUUID(entrepreneurshipId: String?): UUID? {
        if (entrepreneurshipId.isNullOrEmpty()) {
            _actionState.value = EntrepreneurshipBasicActionState.Error("ID del emprendimiento inválido")
            return null
        }

        return try {
            UUID.fromString(entrepreneurshipId)
        } catch (e: Exception) {
            _actionState.value = EntrepreneurshipBasicActionState.Error("ID del emprendimiento inválido")
            null
        }
    }

    fun loadEntrepreneurship(entrepreneurshipId: String?) {
        val uuid = validateAndGetUUID(entrepreneurshipId) ?: return

        viewModelScope.launch {
            _actionState.value = EntrepreneurshipBasicActionState.Loading
            try {
                val entrepreneurship = getEntrepreneurshipUseCase(uuid)
                _uiState.value = EntrepreneurshipBasicUiState(
                    id = entrepreneurship.id ?: UUID.randomUUID(),
                    name = entrepreneurship.name,
                    email = entrepreneurship.email,
                    phone = entrepreneurship.phone,
                    customization = entrepreneurship.customization
                )
                _actionState.value = EntrepreneurshipBasicActionState.Success
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipBasicActionState.Error("Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }
}