package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

data class UserUiState(
    val name: String = "",
    val email: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val allEntrepreneurships: List<Entrepreneurship> = emptyList(),
    val memberSince: String = "",
)

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getAllEntrepreneurshipUseCase: GetAllEntrepreneurship,
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState(loading = true))
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadAllEntrepreneurships()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            try {
                val userDto = getUserDataUseCase()
                Log.d("UserProfileViewModel", "User data loaded: $userDto")
                _uiState.value = UserUiState(
                    name = "${userDto.firstName} ${userDto.lastName}",
                    email = userDto.email,
                    loading = false
                )
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading user data", e)
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    private fun loadAllEntrepreneurships() {
        viewModelScope.launch {
            try {
                val list = getAllEntrepreneurshipUseCase()
                val memberSince = getEarliestFormatted(list)
                _uiState.value = _uiState.value.copy(
                    allEntrepreneurships = list,
                    memberSince = memberSince
                )
                Log.d("UserProfileViewModel", "Miembro desde: $memberSince")
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error loading all entrepreneurships", e)
            }
        }
    }


    private fun getEarliestFormatted(entrepreneurships: List<Entrepreneurship>): String {
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es"))
        return entrepreneurships
            .map { it.creationDate }
            .minOrNull()
            ?.format(outputFormatter) ?: "N/A"
    }



}