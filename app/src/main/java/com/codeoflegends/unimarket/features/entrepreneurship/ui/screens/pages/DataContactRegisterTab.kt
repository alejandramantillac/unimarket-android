package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeoflegends.unimarket.core.ui.components.DropdownMenuBox
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipUiState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel


@Composable
fun DataContacRegisterTab(state: EntrepreneurshipUiState, viewModel: EntrepreneurshipViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        DropdownMenuBox(
            label = "Estado del Emprendimiento",
            options = state.statusOptions,
            selectedOption = state.entrepreneurshipStatus,
            onOptionSelected = { viewModel.onStatusChanged(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuBox(
            label = "Plan de Suscripción",
            options = state.subscriptionOptions.map { it.name },
            selectedOption = state.subscriptionOptions.firstOrNull { it.id.toString() == state.entrepreneurshipSubscription }?.name
                ?: "Seleccionar",
            onOptionSelected = { selectedName ->
                val selectedPlan = state.subscriptionOptions.find { it.name == selectedName }
                selectedPlan?.let { viewModel.onSubscriptionChanged(it.id.toString()) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

                Column {
                    Text(text = "Redes Sociales",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    state.entrepreneurshipSocialNetworks.forEachIndexed { index, socialNetwork ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            SimpleTextField(
                                value = socialNetwork.media,
                                onValueChange = { viewModel.onSocialNetworkPlatformChanged(index, it) },
                                label = "Plataforma",
                                modifier = Modifier
                                    .weight(1f)
                                    .widthIn(min = 120.dp),
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            SimpleTextField(
                                value = socialNetwork.url,
                                onValueChange = { viewModel.onSocialNetworkChanged(index, it) },
                                label = "URL",
                                modifier = Modifier.weight(2f),
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(onClick = { viewModel.removeSocialNetwork(index) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar red social")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { viewModel.addSocialNetwork() }) {
                        Text("Agregar red social")
                    }
                }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.saveEntrepreneurship() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Emprendimiento")
        }
    }
}