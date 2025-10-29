package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.R
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.components.RatingStars
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.UserProfileViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipItem
import coil.compose.AsyncImage
import com.codeoflegends.unimarket.core.ui.components.AppBarOptions
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.UserProfileActionState
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun EntrepreneurshipProfileScreen(
    manager: NavigationManager,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val actionState = viewModel.actionState.collectAsState().value

    android.util.Log.d("UserProfile", ">>> UI: Renderizando EntrepreneurshipProfileScreen <<<")
    android.util.Log.d("UserProfile", ">>> UI: ActionState = $actionState <<<")
    android.util.Log.d("UserProfile", ">>> UI: Usuario - firstName: '${state.user.firstName}' <<<")
    android.util.Log.d("UserProfile", ">>> UI: Usuario - lastName: '${state.user.lastName}' <<<")
    android.util.Log.d("UserProfile", ">>> UI: Usuario - email: '${state.user.email}' <<<")
    android.util.Log.d("UserProfile", ">>> UI: Usuario - profilePicture: '${state.user.profile.profilePicture}' <<<")
    android.util.Log.d("EmprendimientosPropios", ">>> UI: Número de emprendimientos en state = ${state.entrepreneurships.size} <<<")
    state.entrepreneurships.forEachIndexed { index, entrepreneurship ->
        android.util.Log.d("EmprendimientosPropios", ">>> UI: Emprendimiento[$index] = ${entrepreneurship.name} (${entrepreneurship.id}) <<<")
    }

    MainLayout(
        barOptions = AppBarOptions(
            show = true,
            showBackButton = true,
            onBackClick = { manager.navController.popBackStack() }
        ),
        addPadding = true
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        viewModel.refreshEntrepreneurships()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
            }
            android.util.Log.d("EmprendimientosPropios", ">>> UI: Pasando ${state.entrepreneurships.size} items a InfiniteScrollList <<<")
            InfiniteScrollList(
                items = state.entrepreneurships,
                onLoadMore = { },
                isLoading = actionState is UserProfileActionState.Loading,
                headerContent = {
                    Column {
                        // Profile Header Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Profile Header Section
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .padding(end = 16.dp),
                                        shape = MaterialTheme.shapes.small,
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        AsyncImage(
                                            model = state.user.profile.profilePicture,
                                            contentDescription = "Foto de perfil",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        val fullName = "${state.user.firstName} ${state.user.lastName}".trim()
                                        Text(
                                            text = if (fullName.isNotEmpty()) fullName else "Usuario sin nombre",
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        RatingStars(4.5f)
                                        Text(
                                            "(128 reseñas)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Mis Emprendimientos",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        if (state.entrepreneurships.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Todavía no tienes ningún emprendimiento registrado",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                },
                itemContent = { entrepreneurship ->
                    EntrepreneurshipItem(
                        entrepreneurship = entrepreneurship,
                        onClick = {
                            manager.navController.navigate(
                                Routes.ManageEntrepreneurship.createRoute(entrepreneurship.id.toString())
                            )
                        }
                    )
                },
            )

            FloatingActionButton(
                onClick = {
                    manager.navController.navigate(Routes.EntrepreneurshipForm.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar emprendimiento",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
