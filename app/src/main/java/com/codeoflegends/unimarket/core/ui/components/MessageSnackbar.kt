package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.theme.*

@Composable
fun MessageSnackbar(
        message: String,
        isError: Boolean = false,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
        onDismiss: () -> Unit
) {
        if (message.isBlank()) return

        val isDarkTheme = MaterialTheme.colorScheme.background.red < 0.1f

        val containerColor =
                if (isError) {
                        if (isDarkTheme) errorContainerDark else errorContainerLight
                } else {
                        if (isDarkTheme) successContainerDark else successContainerLight
                }

        val contentColor =
                if (isError) {
                        if (isDarkTheme) onErrorContainerDark else onErrorContainerLight
                } else {
                        if (isDarkTheme) onSuccessContainerDark else onSuccessContainerLight
                }

        Surface(
                modifier = Modifier.padding(16.dp),
                color = containerColor,
                shape = MaterialTheme.shapes.small,
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
        ) {
                Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Icon(
                                imageVector =
                                        if (isError) Icons.Default.Warning
                                        else Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = contentColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                                text = message,
                                color = contentColor,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        if (actionLabel != null) {
                                TextButton(
                                        onClick = { onAction?.invoke() },
                                        contentPadding =
                                                PaddingValues(
                                                        horizontal = 8.dp,
                                                        vertical = 4.dp
                                                )
                                ) {
                                        Text(
                                                actionLabel,
                                                color = contentColor,
                                                style =
                                                        MaterialTheme.typography
                                                                .labelMedium
                                        )
                                }
                        }

                        IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.size(32.dp)
                        ) {
                                Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Cerrar",
                                        tint = contentColor,
                                        modifier = Modifier.size(16.dp)
                                )
                        }
                }
        }
}
