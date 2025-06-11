package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.state.ErrorHandler
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import com.codeoflegends.unimarket.features.entrepreneurship.domain.usecase.GetPartnersByEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.repository.PartnerRepository
import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.core.data.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import retrofit2.HttpException

sealed class PartnerUiState {
    object Loading : PartnerUiState()
    data class Success(val partners: List<Partner>) : PartnerUiState()
    data class Error(val message: String) : PartnerUiState()
    object DeletionSuccess : PartnerUiState()
    object AdditionSuccess : PartnerUiState()
}

@HiltViewModel
class PartnerViewModel @Inject constructor(
    private val getPartnersByEntrepreneurshipUseCase: GetPartnersByEntrepreneurshipUseCase,
    private val partnerRepository: PartnerRepository,
    private val userService: UserService
) : ViewModel() {
    private val _uiState = MutableStateFlow<PartnerUiState>(PartnerUiState.Loading)
    val uiState: StateFlow<PartnerUiState> = _uiState.asStateFlow()

    private var currentEntrepreneurshipId: UUID? = null

    fun loadPartners(entrepreneurshipId: UUID) {
        currentEntrepreneurshipId = entrepreneurshipId
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

    fun deletePartner(partnerId: UUID) {
        viewModelScope.launch {
            try {
                _uiState.value = PartnerUiState.Loading
                partnerRepository.deletePartner(partnerId)
                _uiState.value = PartnerUiState.DeletionSuccess
                // Recargar la lista después de eliminar
                currentEntrepreneurshipId?.let { loadPartners(it) }
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    403 -> "No tienes permisos para eliminar colaboradores."
                    401 -> "Tu sesión ha expirado. Por favor, vuelve a iniciar sesión."
                    else -> "Error al eliminar el colaborador: ${e.message()}"
                }
                _uiState.value = PartnerUiState.Error(errorMessage)
                ErrorHandler.handleError(e)
            } catch (e: Exception) {
                _uiState.value = PartnerUiState.Error("Error al eliminar el colaborador: ${e.message}")
                ErrorHandler.handleError(e)
            }
        }
    }

    fun addPartner(email: String, role: String) {
        viewModelScope.launch {
            try {
                _uiState.value = PartnerUiState.Loading
                Log.d("PartnerViewModel", "Buscando usuario con email: $email")
                
                // Buscar usuario por email
                val userResponse = userService.findUserByEmail(email)
                val userId = userResponse.data.firstOrNull()?.id
                    ?: throw Exception("No se encontró ningún usuario con ese correo electrónico")
                
                Log.d("PartnerViewModel", "Usuario encontrado con ID: $userId")

                val entrepreneurshipId = currentEntrepreneurshipId 
                    ?: throw Exception("No hay un emprendimiento seleccionado")

                // Crear el nuevo partner
                val newPartner = Partner(
                    id = UUID.randomUUID(),
                    role = role,
                    user = User(
                        id = UUID.fromString(userId),
                        firstName = "",  // Estos campos se llenarán cuando se cargue la lista
                        lastName = "",
                        email = "",
                        profile = UserProfile(
                            profilePicture = "",
                            userRating = 0f,
                            partnerRating = 0f,
                            registrationDate = LocalDateTime.now()
                        )
                    ),
                    entrepreneurshipId = entrepreneurshipId
                )

                Log.d("PartnerViewModel", "Creando partner con ID: ${newPartner.id}, role: ${newPartner.role}, userId: ${newPartner.user.id}, entrepreneurshipId: ${newPartner.entrepreneurshipId}")

                // Guardar el nuevo partner
                partnerRepository.createPartner(newPartner)
                _uiState.value = PartnerUiState.AdditionSuccess
                
                // Recargar la lista después de agregar
                loadPartners(entrepreneurshipId)
            } catch (e: HttpException) {
                Log.e("PartnerViewModel", "Error HTTP: ${e.code()} - ${e.message()}")
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("PartnerViewModel", "Error body: $errorBody")
                
                val errorMessage = when (e.code()) {
                    403 -> "No tienes permisos para agregar colaboradores."
                    401 -> "Tu sesión ha expirado. Por favor, vuelve a iniciar sesión."
                    404 -> "No se encontró ningún usuario con ese correo electrónico."
                    else -> "Error al agregar el colaborador: ${e.message()}"
                }
                _uiState.value = PartnerUiState.Error(errorMessage)
                ErrorHandler.handleError(e)
            } catch (e: Exception) {
                Log.e("PartnerViewModel", "Error al agregar partner", e)
                _uiState.value = PartnerUiState.Error("Error al agregar el colaborador: ${e.message}")
                ErrorHandler.handleError(e)
            }
        }
    }
} 