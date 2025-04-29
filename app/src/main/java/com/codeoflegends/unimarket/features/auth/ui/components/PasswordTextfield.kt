package com.codeoflegends.unimarket.features.auth.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Visibility
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Contraseña"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    SimpleTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.TwoTone.VisibilityOff
                    else
                        Icons.TwoTone.Visibility,
                    contentDescription = if (passwordVisible)
                        "Ocultar contraseña"
                    else
                        "Mostrar contraseña"
                )
            }
        }
    )
}