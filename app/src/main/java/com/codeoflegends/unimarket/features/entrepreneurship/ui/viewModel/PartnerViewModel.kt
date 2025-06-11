package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.state.ErrorHandler
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import com.codeoflegends.unimarket.features.entrepreneurship.domain.usecase.GetPartnersByEntrepreneurshipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import retrofit2.HttpException

sealed class PartnerUiState {
    object Loading : PartnerUiState()
    data class Success(val partners: List<Partner>) : PartnerUiState()
    data class Error(val message: String) : PartnerUiState()
}

@HiltViewModel
class PartnerViewModel @Inject constructor(
    private val getPartnersByEntrepreneurshipUseCase: GetPartnersByEntrepreneurshipUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<PartnerUiState>(PartnerUiState.Loading)
    val uiState: StateFlow<PartnerUiState> = _uiState.asStateFlow()

    fun loadPartners(entrepreneurshipId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = PartnerUiState.Loading
                Log.d("PartnerViewModel", "Loading partners for entrepreneurship: $entrepreneurshipId")
                val partners = getPartnersByEntrepreneurshipUseCase(entrepreneurshipId)
                _uiState.value = PartnerUiState.Success(partners)
                Log.d("PartnerViewModel", "Successfully loaded ${partners.size} partners")
            } catch (e: HttpException) {
                Log.e("PartnerViewModel", "HTTP Error: ${e.code()} - ${e.message()}")
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("PartnerViewModel", "Error body: $errorBody")
                
                val errorMessage = when (e.code()) {
                    403 -> "No tienes permisos para ver los colaboradores. Por favor, verifica que hayas iniciado sesión y tengas los permisos necesarios."
                    401 -> "Tu sesión ha expirado. Por favor, vuelve a iniciar sesión."
                    else -> "Error al cargar los colaboradores: ${e.message()}"
                }
                
                _uiState.value = PartnerUiState.Error(errorMessage)
                ErrorHandler.handleError(e)
            } catch (e: Exception) {
                Log.e("PartnerViewModel", "Error loading partners", e)
                _uiState.value = PartnerUiState.Error("Error al cargar los colaboradores: ${e.message}")
                ErrorHandler.handleError(e)
            }
        }
    }
} 