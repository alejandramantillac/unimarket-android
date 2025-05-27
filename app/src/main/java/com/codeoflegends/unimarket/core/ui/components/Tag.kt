package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

enum class TagType(
    val displayName: String,
    val icon: ImageVector,
    val color: Long // Color en formato Long (0xAARRGGBB)
) {
    TECHNOLOGY(
        "tecnología",
        Icons.Default.Devices,
        0xFF2196F3
    ),
    ORGANIC(
        "orgánica",
        Icons.Default.Spa,
        0xFF4CAF50
    ),
    SWEETS(
        "dulces",
        Icons.Default.Cake,
        0xFFE91E63
    ),
    HEALTHY_FOOD(
        "comida saludable",
        Icons.Default.Restaurant,
        0xFF8BC34A
    ),
    DESSERTS(
        "postres",
        Icons.Default.Icecream,
        0xFF9C27B0
    ),
    SNACKS(
        "snacks",
        Icons.Default.LocalCafe,
        0xFFFF9800
    ),
    BEVERAGES(
        "bebidas",
        Icons.Default.LocalBar,
        0xFF00BCD4
    ),
    VEGAN(
        "vegana",
        Icons.Default.Eco,
        0xFF009688
    ),
    FOOD(
        "comida",
        Icons.Default.Restaurant,
        0xFFF44336
    ),
    MORE(
        "",
        Icons.Default.MoreHoriz,
        0xFF9E9E9E
    ),
    OTHER(
        "Default",
        Icons.Default.Category,
        0xFF607D8B
    );

    companion object {
        fun fromName(name: String): TagType = 
            values().find { it.displayName == name.lowercase() } ?: OTHER
    }
}

@Composable
fun Tag(
    tagType: TagType,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
        alpha = 0.7f
    )
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        modifier = modifier.height(36.dp),
        shadowElevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        onClick = onClick ?: {}
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = tagType.icon,
                contentDescription = tagType.displayName,
                modifier = Modifier.size(16.dp),
                tint = contentColor
            )
            if (!tagType.displayName.isEmpty()) {
                Text(
                    text = tagType.displayName.capitalize(),
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

