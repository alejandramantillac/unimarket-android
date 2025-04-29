package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.core.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun MessageSnackbar(
    message: String,
    isError: Boolean = false,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    dismissTimeSeconds: Float = -1f,
    onDismiss: () -> Unit
) {
    if (message.isBlank()) return

    val isDarkTheme = MaterialTheme.colorScheme.background.red < 0.1f
    val density = LocalDensity.current
    var isVisible by remember { mutableStateOf(true) }

    // Time left for the snackbar to be dismissed
    var timeLeft by remember { mutableFloatStateOf(dismissTimeSeconds) }
    val showTimer = dismissTimeSeconds > 0

    // Animation for the snackbar
    val offsetAnimation = remember {
        Animatable(initialValue = with(density) { -100.dp.toPx() })
    }

    // Animation for the snackbar
    LaunchedEffect(key1 = isVisible) {
        if (isVisible) {
            offsetAnimation.animateTo(
                targetValue = 0f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        } else {
            offsetAnimation.animateTo(
                targetValue = with(density) { -100.dp.toPx() },
                animationSpec = tween(200, easing = FastOutLinearInEasing)
            )
            onDismiss()
        }
    }

    // Dismiss the snackbar after the specified time
    LaunchedEffect(key1 = message) {
        if (dismissTimeSeconds > 0) {
            timeLeft = dismissTimeSeconds
            while (timeLeft > 0 && isVisible) {
                delay(10)
                timeLeft -= 0.01f
            }
            if (isVisible) {
                isVisible = false
            }
        }
    }

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

    Box(
        modifier = Modifier
            .offset(y = with(density) { offsetAnimation.value.toDp() })
    ) {
        Column {
            Surface(
                modifier = Modifier.padding(16.dp),
                color = containerColor,
                shape = MaterialTheme.shapes.small,
                tonalElevation = 0.dp,
                shadowElevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
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

                    if (showTimer) {
                        val progress = (timeLeft / dismissTimeSeconds).coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp),
                            color = contentColor,
                            trackColor = contentColor.copy(alpha = 0.2f)
                        )
                    }
                }
            }
        }
    }
}