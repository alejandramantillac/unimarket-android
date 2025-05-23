package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages

import androidx.compose.runtime.Composable
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipBasicUiState

@Composable
fun EntrepreneurshipMembersPage(
    basicState: EntrepreneurshipBasicUiState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Pantalla de Miembros",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}