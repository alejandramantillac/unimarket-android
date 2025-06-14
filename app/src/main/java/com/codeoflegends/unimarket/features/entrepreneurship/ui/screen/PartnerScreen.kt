package com.codeoflegends.unimarket.features.entrepreneurship.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.PartnerUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.PartnerViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerScreen(
    entrepreneurshipId: UUID,
    viewModel: PartnerViewModel = hiltViewModel(),
    manager: NavigationManager
) {
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddPartnerDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { email, role ->
                viewModel.addPartner(email, role)
                showAddDialog = false
            }
        )
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        viewModel.loadPartners(entrepreneurshipId)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar colaborador")
            }
        }
    ) { padding ->
        when (uiState) {
            is PartnerUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is PartnerUiState.Success -> {
                val partners = (uiState as PartnerUiState.Success).partners
                if (partners.isEmpty()) {
                    EmptyPartnerState(modifier = Modifier.padding(padding))
                } else {
                    PartnerList(partners, modifier = Modifier.padding(padding))
                }
            }
            is PartnerUiState.Error -> {
                val error = (uiState as PartnerUiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadPartners(entrepreneurshipId) }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            is PartnerUiState.DeletionSuccess -> {
                // No necesitamos hacer nada aquí porque el ViewModel ya recarga la lista
            }
            is PartnerUiState.AdditionSuccess -> {
                // No necesitamos hacer nada aquí porque el ViewModel ya recarga la lista
            }
        }
    }
}

@Composable
private fun PartnerList(partners: List<Partner>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(top = 56.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(partners) { partner ->
            PartnerItem(partner = partner)
        }
    }
}

@Composable
private fun PartnerItem(partner: Partner, viewModel: PartnerViewModel = hiltViewModel()) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar a ${partner.user.firstName} ${partner.user.lastName} como colaborador?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deletePartner(partner.id)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${partner.user.firstName} ${partner.user.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = partner.role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = partner.user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            IconButton(
                onClick = { showDeleteDialog = true }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun EmptyPartnerState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.People,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No hay colaboradores",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Agrega colaboradores a tu emprendimiento",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPartnerDialog(
    onDismiss: () -> Unit,
    onConfirm: (email: String, role: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var roleError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar colaborador") },
        text = {
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = null
                    },
                    label = { Text("Correo electrónico") },
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = role,
                    onValueChange = {
                        role = it
                        roleError = null
                    },
                    label = { Text("Rol") },
                    isError = roleError != null,
                    supportingText = roleError?.let { { Text(it) } },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    var hasError = false
                    if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "Ingresa un correo electrónico válido"
                        hasError = true
                    }
                    if (role.isBlank()) {
                        roleError = "El rol es requerido"
                        hasError = true
                    }
                    if (!hasError) {
                        onConfirm(email, role)
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}