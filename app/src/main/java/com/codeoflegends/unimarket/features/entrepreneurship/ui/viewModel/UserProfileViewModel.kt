package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

data class UserUiState(
    val user: User = User(
        id = UUID.randomUUID(),
        firstName = "",
        lastName = "",
        email = "",
        profile = UserProfile()
    ),
    val entrepreneurships: List<Entrepreneurship> = emptyList(),
)

sealed class UserProfileActionState {
    object Idle : UserProfileActionState()
    data class Error(val message: String) : UserProfileActionState()
    object Loading : UserProfileActionState()
}

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getAllEntrepreneurshipUseCase: GetAllEntrepreneurship,
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<UserProfileActionState>(UserProfileActionState.Loading)
    val actionState: StateFlow<UserProfileActionState> = _actionState.asStateFlow()

    init {
        loadUserData()
        loadAllEntrepreneurships()
    }

    fun refreshEntrepreneurships() {
        loadAllEntrepreneurships()
    }

    private fun loadUserData() {
        _actionState.value = UserProfileActionState.Loading
        viewModelScope.launch {
            try {
                Log.d("UserProfile", "=== INICIO loadUserData ===")
                val userDto = getUserDataUseCase()
                Log.d("UserProfile", "UserDTO recibido: id=${userDto.id}")
                Log.d("UserProfile", "firstName: '${userDto.firstName}'")
                Log.d("UserProfile", "lastName: '${userDto.lastName}'")
                Log.d("UserProfile", "email: '${userDto.email}'")
                Log.d("UserProfile", "profile: ${userDto.profile}")
                Log.d("UserProfile", "profilePicture: '${userDto.profile?.profilePicture}'")
                
                val user = User(
                    id = UUID.fromString(userDto.id),
                    firstName = userDto.firstName.orEmpty(),
                    lastName = userDto.lastName.orEmpty(),
                    email = userDto.email.orEmpty(),
                    profile = userDto.profile?.let { profileDto ->
                        UserProfile(
                            profilePicture = profileDto.profilePicture.orEmpty(),
                            userRating = profileDto.userRating ?: 0f,
                            partnerRating = profileDto.partnerRating ?: 0f,
                            registrationDate = try {
                                LocalDateTime.parse(profileDto.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                            } catch (e: Exception) {
                                LocalDateTime.now()
                            }
                        )
                    } ?: UserProfile()
                )
                
                Log.d("UserProfile", "Usuario mapeado:")
                Log.d("UserProfile", "  - Nombre completo: '${user.firstName} ${user.lastName}'")
                Log.d("UserProfile", "  - Foto perfil: '${user.profile.profilePicture}'")
                
                _uiState.value = _uiState.value.copy(user = user)
                
                Log.d("UserProfile", "UI State actualizado - nombre: '${_uiState.value.user.firstName} ${_uiState.value.user.lastName}'")
                Log.d("UserProfile", "=== FIN loadUserData ===")
                
                _actionState.value = UserProfileActionState.Idle
            } catch (e: Exception) {
                Log.e("UserProfile", "!!! ERROR al cargar datos del usuario !!!")
                Log.e("UserProfile", "Mensaje: ${e.message}")
                Log.e("UserProfile", "Stack trace:", e)
                _actionState.value = UserProfileActionState.Error("Error al cargar el usuario: ${e.message}")
            }
        }
    }

    private fun loadAllEntrepreneurships() {
        viewModelScope.launch {
            try {
                Log.d("EmprendimientosPropios", "=== INICIO loadAllEntrepreneurships ===")
                Log.d("EmprendimientosPropios", "Llamando a getAllEntrepreneurshipUseCase...")
                
                val list = getAllEntrepreneurshipUseCase()
                
                Log.d("EmprendimientosPropios", "Emprendimientos obtenidos: ${list.size}")
                list.forEachIndexed { index, entrepreneurship ->
                    Log.d("EmprendimientosPropios", "[$index] ID: ${entrepreneurship.id}, Nombre: ${entrepreneurship.name}")
                }

                _uiState.value = _uiState.value.copy(
                    entrepreneurships = list
                )
                
                Log.d("EmprendimientosPropios", "UI State actualizado con ${list.size} emprendimientos")
                Log.d("EmprendimientosPropios", "=== FIN loadAllEntrepreneurships ===")
            } catch (e: Exception) {
                Log.e("EmprendimientosPropios", "!!! ERROR al cargar emprendimientos !!!")
                Log.e("EmprendimientosPropios", "Tipo de error: ${e.javaClass.simpleName}")
                Log.e("EmprendimientosPropios", "Mensaje: ${e.message}")
                Log.e("EmprendimientosPropios", "Stack trace:", e)
            }
        }
    }
}