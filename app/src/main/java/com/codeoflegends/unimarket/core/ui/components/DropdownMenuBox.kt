package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    val density = LocalDensity.current
    val isDarkTheme = isSystemInDarkTheme()
    
    val menuContainerColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    }
    
    // Log para depuración
    Log.d("DropdownMenuBox", "Label: $label, Options size: ${options.size}")
    Log.d("DropdownMenuBox", "Options: $options")
    Log.d("DropdownMenuBox", "Selected Option: $selectedOption")

    Box(modifier = modifier) {
        TextField(
            value = selectedOption ?: "",
            onValueChange = {},
            label = { Text(label, color = Color.Gray, style = style, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    expanded = true
                    Log.d("DropdownMenuBox", "Dropdown clicked, expanded = $expanded")
                 }
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            singleLine = true,
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontSize = 14.sp,
                lineHeight = 18.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                disabledIndicatorColor = Color.LightGray,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray,
            ),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Text(
                        text = "▼",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            placeholder = null,
            visualTransformation = VisualTransformation.None,
            shape = MaterialTheme.shapes.small
        )
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { 
                expanded = false 
                Log.d("DropdownMenuBox", "Dropdown dismissed, expanded = $expanded")
            },
            modifier = Modifier
                .width(with(density) { textFieldSize.width.toDp() })
                .heightIn(max = 300.dp)
                .background(
                    color = menuContainerColor,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            if (options.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "No hay opciones disponibles",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onClick = { }
                )
            } else {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                            Log.d("DropdownMenuBox", "Option selected: $option")
                        }
                    )
                }
            }
        }
    }
}