package com.codeoflegends.unimarket.features.entrepreneurship.ui.screens.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeoflegends.unimarket.features.entrepreneurship.ui.components.RatingBar

@Composable
fun EntrepreneurProfileScreen() {
    // Datos simulados por ahora
    val userName = "José David Hurtado"
    val rating = 4.5f
    val reviews = 124
    val totalSales = 1580
    val memberSince = 2023
    val ventures = listOf(
        Triple("UniBites", "Sabores que impulsan tu día", Pair(5, 289)),
        Triple("StyleU", "Tu estilo, tu esencia", Pair(24, 120)),
        Triple("SnackSmart", "Pica inteligente, vive mejor", Pair(10, 80))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        item {
            ProfileHeader(
                userName = userName,
                rating = rating,
                reviews = reviews,
                totalSales = totalSales,
                memberSince = memberSince,
                onEditClick = { /* TODO */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SummaryGraph()
            Spacer(modifier = Modifier.height(16.dp))
            PremiumUpgradeSection()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Mis emprendimientos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(ventures) { (name, slogan, stats) ->
            VentureCard(name, slogan, stats.first, stats.second)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ProfileHeader(
    userName: String,
    rating: Float,
    reviews: Int,
    totalSales: Int,
    memberSince: Int,
    onEditClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = android.R.drawable.sym_def_app_icon),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(userName, fontWeight = FontWeight.Bold)
        RatingBar(rating)
        Text("★ $rating ($reviews reseñas)", fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Ventas totales", fontSize = 12.sp, color = Color.Gray)
                Text("$totalSales", fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Miembro desde", fontSize = 12.sp, color = Color.Gray)
                Text("$memberSince", fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun SummaryGraph() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFFD1C4E9), Color(0xFF7E57C2))
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {

        Text(
            text = "Resumen del mes (gráfico aquí)",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}

@Composable
fun PremiumUpgradeSection() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("¡Desbloquea Todo Tu Potencial!", fontWeight = FontWeight.Bold)
                Text("Aumenta tus ventas con suscripciones premium", fontSize = 12.sp)
            }
            Button(onClick = { /* TODO */ }) {
                Text("Mejorar")
            }
        }
    }
}

@Composable
fun VentureCard(name: String, slogan: String, products: Int, sales: Int) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                painter = painterResource(id = android.R.drawable.sym_def_app_icon),
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text("\"$slogan\"", fontSize = 12.sp, color = Color.Gray)
                RatingBar(4.5f)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text("Productos: $products", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Ventas: $sales", fontSize = 12.sp)
                }
            }
        }
    }
}

