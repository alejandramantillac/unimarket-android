package com.codeoflegends.unimarket.features.entrepreneurship.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MonthlySummaryChart() {
    val barHeights = listOf(100f, 200f, 170f, 190f, 160f, 130f, 180f)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resumen del mes", style = MaterialTheme.typography.titleMedium)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 16.dp)
        ) {
            val barWidth = size.width / (barHeights.size * 2)
            barHeights.forEachIndexed { index, height ->
                drawRect(
                    color = Color(0xFF7E57C2),
                    topLeft = Offset(
                        x = index * barWidth * 2 + barWidth / 2,
                        y = size.height - height
                    ),
                    size = Size(barWidth, height)
                )
            }
        }
    }
}