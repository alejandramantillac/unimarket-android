package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.core.ui.state.ToastHandler
import com.codeoflegends.unimarket.core.validation.FormField
import com.codeoflegends.unimarket.core.validation.FormState
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyValidator
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.DataContactRegisterTab
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipUiState

@Composable
fun EntrepreneurshipFormScreen(
    viewModel: EntrepreneurshipViewModel = hiltViewModel(),
    manager: NavigationManager? = null
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    val formState = remember(state) {
        FormState(
            fields = mapOf(
                "name" to FormField(state.entrepreneurshipName, listOf(NotEmptyValidator("El nombre es obligatorio"))),
                "description" to FormField(state.entrepreneurshipDescription, listOf(
                    NotEmptyValidator("La descripción es obligatoria")
                )),
                "category" to FormField(state.entrepreneurshipCategory, listOf(NotEmptyValidator("Selecciona una categoría")))
            )
        )
    }
    val isFormValid = remember(state) { formState.validateAll() }

    LaunchedEffect(actionState) {
        when (actionState) {
            is EntrepreneurshipActionState.Success -> {
                ToastHandler.showSuccess("¡Emprendimiento guardado!")
                manager?.navController?.popBackStack()
            }
            is EntrepreneurshipActionState.Error -> {
                ToastHandler.handleError((actionState as EntrepreneurshipActionState.Error).message)
            }
            else -> {}
        }
    }

    if (actionState is EntrepreneurshipActionState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Datos del Emprendimiento",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        var selectedTab by remember { mutableStateOf(0) }
        TabSelector(
            tabs = listOf(
                Tab("Básico") {
                    EntrepreneurshipBasicTab(state, viewModel)
                },
                Tab("Contactos") {
                    DataContacRegisterTab(state, viewModel)
                }
            ),
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

    }
}

