package com.codeoflegends.unimarket.features.entrepreneurship.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
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
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.SecondaryButton

@Composable
fun AddReviewButton(
    onReviewSubmitted: (rating: Int, comment: String) -> Unit = { _, _ -> }
) {
    var showDialog by remember { mutableStateOf(false) }
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    MainButton(
        text = "Agregar reseña",
        onClick = { showDialog = true },
        leftIcon = Icons.Default.Add
    )

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Las reseñas son publicas y contienen la información de tu cuenta",
                    )

                    Row {
                        repeat(5) { index ->
                            IconButton(
                                onClick = { rating = index + 1 }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Estrella ${index + 1}",
                                    tint = if (index < rating) Color(0xFFFFD700) else Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Describe tu experiencia (opcional)",
                                color = Color.Gray
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        minLines = 3,
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SecondaryButton(
                            text = "Cancelar",
                            onClick = { showDialog = false },
                            modifier = Modifier.weight(1f),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MainButton(
                            onClick = {
                                if (rating > 0) {
                                    onReviewSubmitted(rating, comment)
                                    showDialog = false
                                    rating = 0
                                    comment = ""
                                }
                            },
                            enabled = rating > 0,
                            text = "Enviar",
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
} 