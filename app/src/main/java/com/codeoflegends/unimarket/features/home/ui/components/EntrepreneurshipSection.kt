package com.codeoflegends.unimarket.features.home.ui.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
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
    orientation: Orientation = Orientation.Horizontal
) {
    val state by viewModel.uiState.collectAsState()
    val pageState by viewModel.actionState.collectAsState()

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
        )
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