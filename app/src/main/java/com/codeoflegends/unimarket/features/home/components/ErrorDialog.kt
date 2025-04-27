package com.codeoflegends.unimarket.features.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorDialog(
    show: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    if (show) {
        val purpleColor = Color(0xFF7B2CBF)

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Error de inicio de sesión") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Aceptar", color = purpleColor)
                }
            }
        )
    }
}
