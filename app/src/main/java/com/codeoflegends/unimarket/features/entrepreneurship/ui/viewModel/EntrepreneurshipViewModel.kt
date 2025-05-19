package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.CreateEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.DeleteEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneurshipsUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.UpdateEntrepreneurshipUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import android.util.Log

sealed class EntrepreneurshipActionState {
    object Idle : EntrepreneurshipActionState()
    object Success : EntrepreneurshipActionState()
    data class Error(val message: String) : EntrepreneurshipActionState()
    object Loading : EntrepreneurshipActionState()
}

@HiltViewModel
class EntrepreneurshipViewModel @Inject constructor(
    private val createEntrepreneurshipUseCase: CreateEntrepreneurshipUseCase,
    private val updateEntrepreneurshipUseCase: UpdateEntrepreneurshipUseCase,
    private val deleteEntrepreneurshipUseCase: DeleteEntrepreneurshipUseCase,
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase,
    private val getAllEntrepreneurshipViewModel: GetAllEntrepreneurshipsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntrepreneurshipUiState())
    val uiState: StateFlow<EntrepreneurshipUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipActionState>(EntrepreneurshipActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipActionState> = _actionState.asStateFlow()

    fun loadEntrepreneurship(entrepreneurshipId: String?) {
        if (entrepreneurshipId.isNullOrEmpty()) {
            _uiState.value = _uiState.value.copy(entrepreneurship = null)
            return
        }

        val uuid = try {
            UUID.fromString(entrepreneurshipId)
        } catch (e: Exception) {
            null
        }

        Log.i("Debug", "El id del empredimiento es: $uuid")

        if (uuid == null) {
            _actionState.value = EntrepreneurshipActionState.Error("ID del emprendimiento inválido")
            return
        }

        _actionState.value = EntrepreneurshipActionState.Loading

        viewModelScope.launch {
            try {
                val entrepreneurship = getEntrepreneurshipUseCase(uuid)
                Log.i("Debug", "Información del emprendimiento: $entrepreneurship")
                _uiState.value = _uiState.value.copy(
                    id = entrepreneurship.id,
                    name = entrepreneurship.name,
                    slogan = entrepreneurship.slogan,
                    description = entrepreneurship.description,
                    email = entrepreneurship.email,
                    phone = entrepreneurship.phone,
                    profileImg =  entrepreneurship.customization.profileImg,
                    bannerImg = entrepreneurship.customization.bannerImg,
                    color1 = entrepreneurship.customization.color1,
                    color2 = entrepreneurship.customization.color2,
                    tags = entrepreneurship.tags,
                    entrepreneurship = entrepreneurship
                )
                Log.i("Debug", "Información del emprendimiento: $entrepreneurship")
                _actionState.value = EntrepreneurshipActionState.Idle
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipActionState.Error("Error al cargar el emprendimiento: ${e.message}")
                Log.i("Debug", "Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }

    fun onNavigationItemSelected(route: String) {
        _uiState.value = _uiState.value.copy(currentRoute = route)
    }
} 