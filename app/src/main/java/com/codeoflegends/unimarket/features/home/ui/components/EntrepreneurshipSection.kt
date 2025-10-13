package com.codeoflegends.unimarket.features.home.ui.components

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.features.home.ui.viewModel.EntrepreneurshipSectionViewModel
import com.codeoflegends.unimarket.features.home.ui.viewModel.state.HomeActionState

@Composable
fun EntrepreneurshipSection(
    title: String,
    fixedFilters: List<DirectusQuery.Filter>,
    manager: NavigationManager,
    viewModel: EntrepreneurshipSectionViewModel = hiltViewModel(),
    orientation: Orientation = Orientation.Vertical
) {
    val state by viewModel.uiState.collectAsState()
    val pageState by viewModel.actionState.collectAsState()

    // Debug logs
    Log.d("EntrepreneurshipSection", "State: $state")
    Log.d("EntrepreneurshipSection", "PageState: $pageState")

    // Cargar datos inicialmente
    LaunchedEffect(Unit) {
        Log.d("EntrepreneurshipSection", "Cargando datos inicialmente...")
        viewModel.loadMoreEntrepreneurships(fixedFilters)
    }

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 4.dp, bottom = 16.dp)
        )
        
        if (orientation == Orientation.Vertical) {
            // Usar Column normal para evitar anidar LazyColumn dentro de LazyColumn
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.entrepreneurships.isEmpty() && pageState is HomeActionState.Loading) {
                    // Mostrar indicador de carga cuando no hay datos y está cargando
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    state.entrepreneurships.forEach { entrepreneurship ->
                        EntrepreneurshipCard(
                            product = entrepreneurship,
                            orientation = orientation,
                            onClick = {
                                manager.navController.navigate(
                                    Routes.EntrepreneurshipView.createRoute(entrepreneurship.id.toString())
                                )
                            }
                        )
                    }
                    
                    if (pageState is HomeActionState.Loading && state.entrepreneurships.isNotEmpty()) {
                        // Mostrar indicador de carga cuando hay datos y está cargando más
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        } else {
            // Usar InfiniteScrollList para orientación horizontal
            InfiniteScrollList(
                items = state.entrepreneurships,
                isLoading = pageState is HomeActionState.Loading,
                onLoadMore = { viewModel.loadMoreEntrepreneurships(fixedFilters) },
                itemContent = { entrepreneurship ->
                    EntrepreneurshipCard(entrepreneurship, onClick = {
                        manager.navController.navigate(
                            Routes.EntrepreneurshipView.createRoute(entrepreneurship.id.toString())
                        )
                    })
                },
                orientation = orientation
            )
        }
    }
}