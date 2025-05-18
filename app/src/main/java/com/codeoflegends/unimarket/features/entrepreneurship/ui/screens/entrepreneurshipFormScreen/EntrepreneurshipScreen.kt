package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.components.Tab
import com.codeoflegends.unimarket.core.ui.components.TabSelector
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipDetailPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipMembersPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipProductsPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.entrepreneurshipFormScreen.pages.EntrepreneurshipStatisticsPage
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipActionState
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.EntrepreneurshipViewModel
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.ui.components.ProductItem

@Composable
private fun DetailsTab(
    entrepreneurshipName: String,
    entrepreneurshipDescription: String,
    imageUrl: String,
    rating: Float,
    reviewCount: Int,
    products: List<Product>,
    onProductEditClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Imagen del emprendimiento
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Imagen del emprendimiento",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Descripción
        item {
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                text = entrepreneurshipDescription,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Fila de calificación
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Estrellas
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < rating.toInt()) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Estrella ${index + 1}",
                            tint = if (index < rating.toInt()) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Número de calificación
                Text(
                    text = String.format("%.1f", rating),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Número de reseñas
                Text(
                    text = "(${reviewCount} reseñas)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Información adicional de ejemplo
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Información de contacto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("📧 contacto@fashionstore.com")
                    Text("📱 +1 (234) 567-8900")
                    Text("📍 Calle Principal #123, Ciudad")
                }
            }
        }
    }
}

@Composable
fun EntrepreneurshipScreen(
    entrepreneurshipId: String?,
    manager: NavigationManager,
    viewModel: EntrepreneurshipViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()

    LaunchedEffect(entrepreneurshipId) {
        if (entrepreneurshipId != null) {
            viewModel.loadEntrepreneurship(entrepreneurshipId)
        }
    }

    LaunchedEffect(actionState) {
        when (actionState) {
            is EntrepreneurshipActionState.Error -> {
                // Mostrar error
            }
            is EntrepreneurshipActionState.Success -> {
                // Navegar de vuelta
                manager?.navController?.popBackStack()
            }
            else -> {}
        }
    }


    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            title = "Entrepreneurship Name",
            showBackButton = true,
            onBackClick = { manager.navController.popBackStack() }
        ),
        //pageLoading = isLoading
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabSelector(
                tabs = listOf(
                    Tab("Detalles") { EntrepreneurshipDetailPage(viewModel) },
                    Tab("Productos") { EntrepreneurshipProductsPage(viewModel) },
                    Tab("Miembros") { EntrepreneurshipMembersPage(viewModel) },
                    Tab("Estadísticas") { EntrepreneurshipStatisticsPage(viewModel) }
                ),
                selectedTabIndex = state.selectedTab,
                onTabSelected = { viewModel.onTabSelected(it) }
            )
        }
    }
} 