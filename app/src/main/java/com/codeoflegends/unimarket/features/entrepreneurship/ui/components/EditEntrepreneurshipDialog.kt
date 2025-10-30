package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.text.KeyboardOptions
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField

@Composable
fun EditEntrepreneurshipDialog(
    isOpen: Boolean,
    currentName: String,
    currentSlogan: String,
    currentDescription: String,
    currentEmail: String,
    currentPhone: String,
    onDismiss: () -> Unit,
    onConfirm: (name: String, slogan: String, description: String, email: String, phone: String) -> Unit,
    isUpdating: Boolean = false
) {
    if (!isOpen) return

    var name by remember { mutableStateOf(currentName) }
    var slogan by remember { mutableStateOf(currentSlogan) }
    var description by remember { mutableStateOf(currentDescription) }
    var email by remember { mutableStateOf(currentEmail) }
    var phone by remember { mutableStateOf(currentPhone) }

    // Validaciones
    val isNameValid = name.isNotBlank()
    val isSloganValid = slogan.isNotBlank()
    val isDescriptionValid = description.isNotBlank()
    val isEmailValid = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPhoneValid = phone.isNotBlank()
    
    val isFormValid = isNameValid && isSloganValid && isDescriptionValid && isEmailValid && isPhoneValid

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Editar Emprendimiento",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Form content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Nombre
                    Column {
                        SimpleTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "Nombre del emprendimiento",
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isNameValid && name.isNotEmpty()) {
                            Text(
                                text = "El nombre es requerido",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }

                    // Slogan
                    Column {
                        SimpleTextField(
                            value = slogan,
                            onValueChange = { slogan = it },
                            label = "Slogan",
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (!isSloganValid && slogan.isNotEmpty()) {
                            Text(
                                text = "El slogan es requerido",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }

                    // Descripción
                    Column {
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descripción") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            maxLines = 5,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = Color.LightGray,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.primary,
                            )
                        )
                        if (!isDescriptionValid && description.isNotEmpty()) {
                            Text(
                                text = "La descripción es requerida",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }

                    // Email
                    Column {
                        SimpleTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Email",
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                        if (!isEmailValid && email.isNotEmpty()) {
                            Text(
                                text = "Ingrese un email válido",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }

                    // Teléfono
                    Column {
                        SimpleTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = "Teléfono",
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                        if (!isPhoneValid && phone.isNotEmpty()) {
                            Text(
                                text = "El teléfono es requerido",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        enabled = !isUpdating
                    ) {
                        Text("Cancelar")
                    }
                    
                    MainButton(
                        text = if (isUpdating) "Guardando..." else "Guardar",
                        onClick = {
                            if (isFormValid && !isUpdating) {
                                onConfirm(name, slogan, description, email, phone)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = isFormValid && !isUpdating
                    )
                }
                
                if (isUpdating) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

