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
        profile = UserProfile(
            profilePicture = "",
            userRating = 0f,
            partnerRating = 0f,
            registrationDate = LocalDateTime.now()
        )
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

    private fun loadUserData() {
        _actionState.value = UserProfileActionState.Loading
        viewModelScope.launch {
            try {
                val userDto = getUserDataUseCase()
                Log.d("UserProfileViewModel", "User data loaded: $userDto")
                _uiState.value = _uiState.value.copy(
                    user = User(
                        id = UUID.fromString(userDto.id),
                        firstName = userDto.firstName,
                        lastName = userDto.lastName,
                        email = userDto.email,
                        profile = userDto.profile?.let { profileDto ->
                            UserProfile(
                                profilePicture = profileDto.profilePicture,
                                userRating = profileDto.userRating,
                                partnerRating = profileDto.partnerRating,
                                registrationDate = LocalDateTime.parse(profileDto.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                            )
                        } ?: UserProfile("", 0f, 0f, LocalDateTime.now())
                    )
                )
                _actionState.value = UserProfileActionState.Idle
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading user data", e)
                _actionState.value = UserProfileActionState.Error("Error al cargar el usuario: ${e.message}")
            }
        }
    }

    private fun loadAllEntrepreneurships() {
        viewModelScope.launch {
            try {
                val list = getAllEntrepreneurshipUseCase()

                _uiState.value = _uiState.value.copy(
                    entrepreneurships = list
                )
                Log.d("UserProfileViewModel", "Emprendimeintos: $list")
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading all entrepreneurships", e)
            }
        }
    }
}