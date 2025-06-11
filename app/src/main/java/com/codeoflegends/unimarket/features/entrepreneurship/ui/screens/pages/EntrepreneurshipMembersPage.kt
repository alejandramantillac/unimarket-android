package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.*
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.CollaboratorUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.CollaboratorViewModel
import java.util.UUID
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun EntrepreneurshipMembersPage(
    viewModel: CollaboratorViewModel = hiltViewModel(),
    entrepreneurshipId: UUID
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedCollaborator by remember { mutableStateOf<Collaborator?>(null) }

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadCollaborators(entrepreneurshipId)
    }

    CollaboratorsContent(
        uiState = uiState,
        onAddClick = { /* TODO: Implementar agregar colaborador */ },
        onDeleteClick = { collaborator ->
            selectedCollaborator = collaborator
            showDeleteDialog = true
        }
    )

    if (showDeleteDialog) {
        DeleteCollaboratorDialog(
            collaborator = selectedCollaborator,
            onConfirm = {
                // TODO: Implementar eliminación de colaborador
                showDeleteDialog = false
            },
            onDismiss = {
                showDeleteDialog = false
                selectedCollaborator = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CollaboratorsContent(
    uiState: CollaboratorUiState,
    onAddClick: () -> Unit,
    onDeleteClick: (Collaborator) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        when (uiState) {
            is CollaboratorUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is CollaboratorUiState.Success -> {
                if (uiState.collaborators.isEmpty()) {
                    CollaboratorEmptyState(onAddClick = onAddClick)
                } else {
                    CollaboratorsList(
                        collaborators = uiState.collaborators,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
            is CollaboratorUiState.Error -> {
                CollaboratorErrorState(message = uiState.message)
            }
        }
    }
}

@Composable
private fun CollaboratorsList(
    collaborators: List<Collaborator>,
    onDeleteClick: (Collaborator) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp)
    ) {
        Text(
            text = "Equipo de trabajo",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(collaborators) { collaborator ->
                CollaboratorItem(
                    collaborator = collaborator,
                    onDeleteClick = { onDeleteClick(collaborator) }
                )
            }
        }
    }
}