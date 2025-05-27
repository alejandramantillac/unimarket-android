package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.collaborator.GetCollaboratorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class CollaboratorUiState {
    object Loading : CollaboratorUiState()
    data class Success(val collaborators: List<Collaborator>) : CollaboratorUiState()
    data class Error(val message: String) : CollaboratorUiState()
}

@HiltViewModel
class CollaboratorViewModel @Inject constructor(
    private val getCollaboratorsUseCase: GetCollaboratorsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CollaboratorUiState>(CollaboratorUiState.Loading)
    val uiState: StateFlow<CollaboratorUiState> = _uiState.asStateFlow()

    fun loadCollaborators(entrepreneurshipId: UUID) {
        viewModelScope.launch {
            _uiState.value = CollaboratorUiState.Loading
            try {
                val collaborators = getCollaboratorsUseCase(entrepreneurshipId)
                _uiState.value = CollaboratorUiState.Success(collaborators)
            } catch (e: Exception) {
                _uiState.value = CollaboratorUiState.Error("Error al cargar colaboradores: ${e.message}")
            }
        }
    }
} 