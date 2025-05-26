package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollaboratorsScreen(
    viewModel: CollaboratorViewModel,
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Colaboradores") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Implementar navegación hacia atrás */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar colaborador",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
}

@Composable
private fun CollaboratorsList(
    collaborators: List<Collaborator>,
    onDeleteClick: (Collaborator) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No hay colaboradores registrados",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Agrega colaboradores a tu emprendimiento",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                MainButton(
                    onClick = { /* TODO: Implementar agregar colaborador */ },
                    text = "Agregar Colaborador",
                    leftIcon = Icons.Default.Add
                )
            }
        }
    }
}

@Composable
fun ErrorState(
    message: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Default.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollaboratorItem(
    collaborator: Collaborator,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.small,
        onClick = { /* TODO: Implementar edición de colaborador */ }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    modifier = Modifier
                        .size(40.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = collaborator.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = collaborator.role,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                    Text(
                        text = collaborator.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar colaborador",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    collaborator: Collaborator?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (collaborator == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar eliminación") },
        text = { 
            Text("¿Estás seguro que deseas eliminar a ${collaborator.name} como colaborador?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CollaboratorsScreenEmptyPreview() {
    CollaboratorsContent(
        uiState = CollaboratorUiState.Success(emptyList()),
        onAddClick = {},
        onDeleteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CollaboratorsScreenWithDataPreview() {
    val sampleCollaborators = listOf(
        Collaborator(
            id = UUID.randomUUID(),
            name = "Juan Pérez",
            email = "juan.perez@example.com",
            role = "Desarrollador",
            entrepreneurshipId = UUID.randomUUID()

        ),
        Collaborator(
            id = UUID.randomUUID(),
            name = "María García",
            email = "maria.garcia@example.com",
            role = "Diseñadora",
            entrepreneurshipId = UUID.randomUUID()
        ),
        Collaborator(
            id = UUID.randomUUID(),
            name = "Carlos López",
            email = "carlos.lopez@example.com",
            role = "Marketing",
            entrepreneurshipId = UUID.randomUUID()
        )
    )

    CollaboratorsContent(
        uiState = CollaboratorUiState.Success(sampleCollaborators),
        onAddClick = {},
        onDeleteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CollaboratorsScreenErrorPreview() {
    CollaboratorsContent(
        uiState = CollaboratorUiState.Error("Ha ocurrido un error al cargar los colaboradores"),
        onAddClick = {},
        onDeleteClick = {}
    )
} 