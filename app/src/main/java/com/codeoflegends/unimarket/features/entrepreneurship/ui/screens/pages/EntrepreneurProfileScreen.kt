package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.tooling.preview.Preview
import com.codeoflegends.unimarket.core.constant.Routes
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.ClickableTextLink
import com.codeoflegends.unimarket.core.ui.theme.UnimarketTheme
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.RatingBar

data class UserUiModel(
    val name: String,
    val rating: Float,
    val reviews: Int,
    val totalSales: Int,
    val memberSince: Int
)

data class EntrepreneurshipUiModel(
    val name: String,
    val slogan: String,
    val rating: Float,
    val reviews: Int,
    val products: Int,
    val sales: Int,
    val image: Uri
)

@Composable
fun ProfileScreen(manager: NavigationManager) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Icon(Icons.Default.Settings, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp))
            Text("José David Hurtado", style = MaterialTheme.typography.titleMedium)
            RatingBar(4.5f)
            Text("⭐ 4.5 (124 reseñas)", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
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
            Button(onClick = { /* Editar */ }) {
                Text("Editar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Resumen del mes", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFFEDE7F6))
        ) {
            Text("Gráfica aquí", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        EntrepreneurshipItem(manager, "UniBites", "Sabores que impulsan tu día", 5, 289, 4.5f, 50)
        EntrepreneurshipItem(manager, "StyleU", "Tu estilo, tu esencia", 3, 150, 4.5f, 50)
    }
}

@Composable
fun EntrepreneurshipItem(manager: NavigationManager, name: String, description: String, products: Int, sales: Int, rating: Float, reviews: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.LightGray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleSmall)
                Text(description, style = MaterialTheme.typography.bodySmall)
                RatingBar(rating)
                Text("⭐ $rating ($reviews reseñas)", style = MaterialTheme.typography.bodySmall)
                Text("Productos: $products  Ventas: $sales", style = MaterialTheme.typography.bodySmall)
            }
            IconButton({
                manager.navController.navigate(
                    Routes.ManageEntrepreneurship.createRoute("00000000-0000-0000-0000-000000000007")
                )
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}
