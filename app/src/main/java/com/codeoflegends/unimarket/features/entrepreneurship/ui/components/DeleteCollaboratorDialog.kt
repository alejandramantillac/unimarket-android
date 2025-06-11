package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator

@Composable
fun DeleteCollaboratorDialog(
    collaborator: Collaborator?,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (collaborator == null) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar eliminación") },
        text = { 
            Text("¿Estás seguro que deseas eliminar a ${collaborator.user.firstName} ${collaborator.user.lastName} como colaborador?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
} 