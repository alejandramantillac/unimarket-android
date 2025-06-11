package com.codeoflegends.unimarket.features.entrepreneurship.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.PartnerUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.PartnerViewModel
import com.codeoflegends.unimarket.features.auth.ui.components.RequirePermission
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerScreen(
    entrepreneurshipId: UUID,
    viewModel: PartnerViewModel = hiltViewModel(),
    manager: NavigationManager
) {
    RequirePermission(
        permission = "partner.read",
        manager = manager,
        fallback = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No tienes permisos",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "No tienes los permisos necesarios para ver los colaboradores",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(entrepreneurshipId) {
            viewModel.loadPartners(entrepreneurshipId)
        }

        when (uiState) {
            is PartnerUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is PartnerUiState.Success -> {
                val partners = (uiState as PartnerUiState.Success).partners
                if (partners.isEmpty()) {
                    EmptyPartnerState()
                } else {
                    PartnerList(partners)
                }
            }
            is PartnerUiState.Error -> {
                val error = (uiState as PartnerUiState.Error).message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error)
                }
            }
        }
    }
}

@Composable
private fun PartnerList(partners: List<Partner>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(partners) { partner ->
            PartnerItem(partner = partner)
        }
    }
}

@Composable
private fun PartnerItem(partner: Partner) {
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

            IconButton(onClick = { /* TODO: Implementar edición */ }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
        }
    }
}

@Composable
private fun EmptyPartnerState() {
    Column(
        modifier = Modifier
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