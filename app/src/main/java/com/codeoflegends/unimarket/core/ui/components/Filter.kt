package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.window.Dialog
import com.codeoflegends.unimarket.core.ui.viewModel.FilterViewModel

@Composable
fun Filter(
    viewModel: FilterViewModel,
    modifier: Modifier = Modifier,
    showResultMessage: Boolean = true,
    advancedFiltersContent: @Composable (() -> Unit)? = null
) {
    val filterState by viewModel.filterState.collectAsState()
    val focusManager = LocalFocusManager.current
    var tempSearchQuery by remember { mutableStateOf(filterState.searchQuery) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = tempSearchQuery,
            onValueChange = { tempSearchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = {
                Text(
                    text = "Buscar...",
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            trailingIcon = {
                if (tempSearchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        tempSearchQuery = ""
                        viewModel.updateSearchQuery("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpiar búsqueda"
                        )
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.updateSearchQuery(tempSearchQuery)
                    focusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
        )

        // Sección de filtros
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Filtros a la izquierda (scrollable)
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtros dinámicos
                filterState.filters.forEach { filter ->
                    FilterChip(
                        selected = filter.isSelected,
                        onClick = { viewModel.toggleFilter(filter.id) },
                        label = { Text(filter.label) }
                    )
                }
            }

            // Icono de filtros avanzados
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filtros avanzados",
                tint = if (filterState.showAdvancedFilters) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(enabled = advancedFiltersContent != null) {
                        viewModel.toggleAdvancedFilters()
                    }
            )
        }

        if (showResultMessage) {
            Text(
                text = filterState.resultMessage,
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Dialog de filtros avanzados
        if (filterState.showAdvancedFilters) {
            Dialog(
                onDismissRequest = { viewModel.toggleAdvancedFilters() }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.large,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    advancedFiltersContent?.invoke()
                }
            }
        }
    }
} 