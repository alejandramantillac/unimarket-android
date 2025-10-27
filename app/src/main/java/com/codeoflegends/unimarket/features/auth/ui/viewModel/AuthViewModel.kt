package com.codeoflegends.unimarket.features.auth.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthResult
import com.codeoflegends.unimarket.features.auth.data.service.JwtDecoder
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthState
import com.codeoflegends.unimarket.features.auth.data.model.domain.AuthStateType
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.RoleRepository
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
    private val tokenRepository: TokenRepository,
    private val roleRepository: RoleRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    private val _loginEvent = MutableSharedFlow<AuthResult<Unit>>()
    val loginEvent = _loginEvent.asSharedFlow()

    private val _registerEvent = MutableSharedFlow<AuthResult<Unit>>()
    val registerEvent = _registerEvent.asSharedFlow()

    // Estado temporal para el rol de navegación
    private val _selectedRole = MutableStateFlow<String?>(null)
    val selectedRole: StateFlow<String?> = _selectedRole

    init {
        viewModelScope.launch {
            // Verificar el estado de autenticación al inicio
            val isLoggedIn = authRepository.isUserLoggedIn()
            roleRepository.getRolName("")
            updateAuthState(isLoggedIn)

            // Observar cambios en el estado de autenticación y permisos
            authRepository.observeAuthState().collect { state ->
                updateAuthState(state.state == AuthStateType.AUTHENTICATED)
            }
        }
    }

    private suspend fun updateAuthState(isAuthenticated: Boolean) {
        Log.d("AuthViewModel", "=== INICIO updateAuthState ===")
        Log.d("AuthViewModel", "isAuthenticated: $isAuthenticated")
        
        val tokenPayload = if (isAuthenticated) {
            val token = tokenRepository.getAccessToken()
            Log.d("AuthViewModel", "Token recuperado: ${token?.take(50)}...")
            val payload = token?.let { jwtDecoder.decodePayload(it) } ?: JwtDecoder.JwtPayload()
            Log.d("AuthViewModel", "Token Payload - userId: ${payload.userId}, roleId: ${payload.userRole}")
            payload
        } else {
            Log.d("AuthViewModel", "Usuario no autenticado, usando payload vacío")
            JwtDecoder.JwtPayload()
        }

        val roleName = roleRepository.getRolName(tokenPayload.userRole)
        Log.d("AuthViewModel", "Nombre del rol obtenido: '$roleName'")
        
        _authState.value = AuthState(
            state = if (isAuthenticated) AuthStateType.AUTHENTICATED else AuthStateType.ANONYMOUS,
            authorities = roleName,
            userId = tokenPayload.userId
        )
        
        Log.d("AuthViewModel", "AuthState actualizado - authorities: '${_authState.value.authorities}', userId: ${_authState.value.userId}")
        Log.d("AuthViewModel", "=== FIN updateAuthState ===")
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

    fun selectRole(role: String) {
        _selectedRole.value = role
    }
}