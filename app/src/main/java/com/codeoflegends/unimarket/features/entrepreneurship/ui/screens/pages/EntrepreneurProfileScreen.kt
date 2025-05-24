package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.RatingBar
import com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel.UserProfileViewModel
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.EntrepreneurshipItem
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.MonthlySummaryChart

@Composable
fun ProfileScreen(
    manager: NavigationManager,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Icon(Icons.Default.Settings, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp))
            Text(
                state.name ?: "Cargando...",
                style = MaterialTheme.typography.titleMedium
            )
            RatingBar(4.5f)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("1580", style = MaterialTheme.typography.titleSmall)
                    Text("Ventas totales")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("2023", style = MaterialTheme.typography.titleSmall)
                    Text("Miembro desde")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                manager.navController.navigate(Routes.EditProfile.createRoute("id_del_emprendimiento"))
            }) {
                Text("Editar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MonthlySummaryChart()

        Spacer(modifier = Modifier.height(16.dp))
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
        Spacer(modifier = Modifier.height(16.dp))

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




    }
}