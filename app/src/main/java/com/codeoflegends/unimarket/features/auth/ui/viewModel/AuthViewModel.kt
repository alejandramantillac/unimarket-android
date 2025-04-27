package com.codeoflegends.unimarket.features.auth.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.features.auth.data.service.JwtDecoder
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthState
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val jwtDecoder: JwtDecoder,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    private val _loginEvent = MutableSharedFlow<AuthResult<Unit>>()
    val loginEvent = _loginEvent.asSharedFlow()

    private val _registerEvent = MutableSharedFlow<AuthResult<Unit>>()
    val registerEvent = _registerEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            // Verificar el estado de autenticación al inicio
            val isLoggedIn = authRepository.isUserLoggedIn()
            updateAuthState(isLoggedIn)

            // Observar cambios en el estado de autenticación y permisos
            authRepository.observeAuthState().collect { state ->
                updateAuthState(state.state == AuthStateType.AUTHENTICATED)
            }
        }
    }

    private suspend fun updateAuthState(isAuthenticated: Boolean) {
        val tokenPayload = if (isAuthenticated) {
            val token = tokenRepository.getAccessToken()
            Log.d("AuthViewModel", "Token: $token")
            token?.let { jwtDecoder.decodePayload(it) } ?: JwtDecoder.JwtPayload()
        } else {
            JwtDecoder.JwtPayload()
        }

        _authState.value = AuthState(
            state = if (isAuthenticated) AuthStateType.AUTHENTICATED else AuthStateType.ANONYMOUS,
            authorities = tokenPayload.userRole,
            userId = tokenPayload.userId
        )
    }

    suspend fun sendForgotPasswordRequest(email: String): AuthResult<Unit> {
        return authRepository.forgotPassword(email)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.login(email, password)
            Log.d("AuthViewModel", "Login result: $result")
            _authState.value = _authState.value.copy(isLoading = false)
            _loginEvent.emit(result)
        }
    }

    fun register(email: String, password: String){
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true)
            val result = authRepository.register(email, password)
            Log.d("AuthViewModel", "Register result: $result")
            _authState.value = _authState.value.copy(isLoading = false)
            _registerEvent.emit(result)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}