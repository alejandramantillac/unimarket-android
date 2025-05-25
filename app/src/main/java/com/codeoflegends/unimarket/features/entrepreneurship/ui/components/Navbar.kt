package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

sealed class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : NavigationItem(
        route = "home",
        icon = Icons.Default.Home,
        label = "Inicio"
    )
    object Products : NavigationItem(
        route = "products",
        icon = Icons.Default.ShoppingCart,
        label = "Productos"
    )
    object People : NavigationItem(
        route = "people",
        icon = Icons.Default.People,
        label = "Personas"
    )
    object Orders : NavigationItem(
        route = "orders",
        icon = Icons.Default.List,
        label = "Pedidos"
    )
    object Statistics : NavigationItem(
        route = "metrics",
        icon = Icons.Default.BarChart,
        label = "Métricas"
    )
}

@Composable
fun NavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Products,
        NavigationItem.People,
        NavigationItem.Orders,
        //NavigationItem.Statistics
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }
}
