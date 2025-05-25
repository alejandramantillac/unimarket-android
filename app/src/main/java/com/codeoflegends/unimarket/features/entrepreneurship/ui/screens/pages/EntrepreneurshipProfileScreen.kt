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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.codeoflegends.unimarket.core.ui.components.InfiniteScrollList
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.UserProfileActionState
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun EntrepreneurshipProfileScreen(
    manager: NavigationManager,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val actionState = viewModel.actionState.collectAsState().value

    if (actionState is UserProfileActionState.Error) {
        Text(
            text = actionState.message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Icon(Icons.Default.Settings, contentDescription = null)
            }

            // inicio datos usuario
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
                        Text(
                            text = state.user.firstName + " " + state.user.lastName,
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

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    "Ventas totales",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    "1580",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    "Miembro desde",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = state.user.profile.registrationDate.format(
                                        DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es"))
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    MainButton(
                        onClick = {
                            manager.navController.navigate(Routes.EditProfile.createRoute("id_del_emprendimiento"))
                        },
                        text = "Editar Perfil",
                        fillMaxWidth = true,
                        leftIcon = Icons.Default.Edit
                    )
                }
            }
            // fin datos usuario
            //Spacer(modifier = Modifier.height(16.dp))

            /*
            MonthlySummaryChart()
             */
            //Spacer(modifier = Modifier.height(16.dp))
            /*
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEDE7F6), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = Color(0xFF6A1B9A))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("¡Desbloquea Todo Tu Potencial!")
                            Text("Aumenta tus ventas con suscripción premium", style = MaterialTheme.typography.bodySmall)
                        }
                        Button(onClick = { /* Esto esta pendiente por implementar*/ }) {
                            Text("Mejorar")
                        }
                    }
            */
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Mis Emprendimientos",
                style = MaterialTheme.typography.titleMedium,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                InfiniteScrollList(
                    items = state.entrepreneurships,
                    onLoadMore = { },
                    isLoading = actionState is UserProfileActionState.Loading,
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
            }

            /**
             * itemContent = { entrepreneurship ->
             *                 EntrepreneurshipItem(
             *                     entrepreneurship = entrepreneurship,
             *                     onClick = {
             *                         manager.navController.navigate(
             *                             Routes.ManageEntrepreneurship.createRoute(entrepreneurship.id.toString())
             *                         )
             *                     }
             *                 )
             *             },
             *             emptyContent = {
             *                 Text("No tienes emprendimientos aún. ¡Crea uno nuevo!")
             *             }
             */
            /*
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Mis emprendimientos", style = MaterialTheme.typography.titleMedium)
                Text("", color = Color(0xFF6A1B9A))
                ClickableTextLink("+ Nuevo", {
                    manager.navController.navigate(Routes.EntrepreneurshipForm.route)
                })
            }

            Spacer(modifier = Modifier.height(8.dp))


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.allEntrepreneurships) { entrepreneurship ->
                    EntrepreneurshipItem(
                        entrepreneurship = entrepreneurship,
                        onClick = {
                            manager.navController.navigate(
                                Routes.ManageEntrepreneurship.createRoute(entrepreneurship.id.toString())
                            )
                        }
                    )
                }
            }
             */


        }
    }
}