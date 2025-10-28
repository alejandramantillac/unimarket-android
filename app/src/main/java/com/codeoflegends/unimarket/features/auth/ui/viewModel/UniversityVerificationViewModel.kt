package com.codeoflegends.unimarket.features.auth.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.state.ErrorHandler
import com.codeoflegends.unimarket.core.ui.state.MessageManager
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.VerificationRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VerificationUiState(
    val isSubmitting: Boolean = false,
    val submitted: Boolean = false
)

@HiltViewModel
class UniversityVerificationViewModel @Inject constructor(
    private val verificationRepository: VerificationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerificationUiState())
    val uiState: StateFlow<VerificationUiState> = _uiState

    fun submitVerification() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSubmitting = true)
                val result = verificationRepository.sendVerification()
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(isSubmitting = false, submitted = true)
                    MessageManager.showMessage("Verificación enviada correctamente. Por favor, inicia sesión nuevamente.")
                    authRepository.logout()
                } else {
                    _uiState.value = _uiState.value.copy(isSubmitting = false)
                    result.exceptionOrNull()?.let { ErrorHandler.handleError(it) }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isSubmitting = false)
                ErrorHandler.handleError(e)
            }
        }
    }
}


