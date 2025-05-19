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
    ELECTRONICS(
        "Tag 1",
        Icons.Default.Devices,
        0xFF2196F3
    ),
    CLOTHING(
        "Tag 2",
        Icons.Default.ShoppingBag,
        0xFFE91E63
    ),
    FOOD(
        "Tag 3",
        Icons.Default.Restaurant,
        0xFF4CAF50
    ),
    BOOKS(
        "Tag 4",
        Icons.Default.MenuBook,
        0xFF9C27B0
    ),
    SPORTS(
        "Tag 5",
        Icons.Default.SportsSoccer,
        0xFFFF9800
    ),
    BEAUTY(
        "Tag 6",
        Icons.Default.Face,
        0xFFF44336
    ),
    HOME(
        "Tag 7",
        Icons.Default.Home,
        0xFF795548
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

