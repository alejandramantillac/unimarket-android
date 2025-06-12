package com.codeoflegends.unimarket.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductCategoriesSection() {
    val categories = listOf(
        "Moda" to "https://cdn-icons-png.flaticon.com/512/2271/2271341.png",
        "Tecnología" to "https://cdn-icons-png.flaticon.com/512/2777/2777154.png",
        "Hogar" to "https://cdn-icons-png.flaticon.com/512/2261/2261387.png",
        "Juguetes" to "https://cdn-icons-png.flaticon.com/512/3081/3081559.png",
        "Libros" to "https://cdn-icons-png.flaticon.com/512/2436/2436702.png",
        "Mascotas" to "https://cdn-icons-png.flaticon.com/512/3047/3047928.png",
        "Alimentos" to "https://cdn-icons-png.flaticon.com/512/2424/2424045.png",
        "Salud" to "https://cdn-icons-png.flaticon.com/512/2966/2966327.png"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 4
        ) {
            categories.forEachIndexed { index, (name, imageUrl) ->
                CategoryItem(
                    name = name,
                    imageUrl = imageUrl
                )
            }
        }
    }
}