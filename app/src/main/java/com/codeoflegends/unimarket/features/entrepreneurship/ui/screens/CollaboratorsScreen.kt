package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.CollaboratorUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.CollaboratorViewModel
import java.util.UUID

@Composable
fun CollaboratorsScreen(
    viewModel: CollaboratorViewModel,
    entrepreneurshipId: UUID
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadCollaborators(entrepreneurshipId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Colaboradores",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (uiState) {
            is CollaboratorUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CollaboratorUiState.Success -> {
                val collaborators = (uiState as CollaboratorUiState.Success).collaborators
                if (collaborators.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay colaboradores registrados")
                    }
                } else {
                    LazyColumn {
                        items(collaborators) { collaborator ->
                            CollaboratorItem(collaborator = collaborator)
                        }
                    }
                }
            }
            is CollaboratorUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as CollaboratorUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun CollaboratorItem(
    collaborator: Collaborator
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = collaborator.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = collaborator.role,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = collaborator.email,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 