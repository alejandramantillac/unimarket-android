package com.codeoflegends.unimarket.features.auth.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeoflegends.unimarket.core.navigation.NavigationManager
import com.codeoflegends.unimarket.core.ui.components.MainButton
import com.codeoflegends.unimarket.core.ui.components.MainLayout
import com.codeoflegends.unimarket.core.ui.components.SimpleTextField
import com.codeoflegends.unimarket.core.ui.components.DropdownMenuBox
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeoflegends.unimarket.features.auth.ui.viewModel.UniversityVerificationViewModel
import com.codeoflegends.unimarket.core.constant.Routes

// Lista de universidades disponibles
val availableUniversities = listOf(
    "Pontificia Universidad Javeriana",
    "Universidad ICESI"
)

@Composable
fun UniversityVerificationScreen(
    manager: NavigationManager,
    viewModel: UniversityVerificationViewModel = hiltViewModel()
) {
    var currentStep by remember { mutableStateOf(1) }
    var selectedUniversity by remember { mutableStateOf<String?>(null) }
    var documentUri by remember { mutableStateOf<Uri?>(null) }
    var carnetUri by remember { mutableStateOf<Uri?>(null) }
    var selfieUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    // Image picker launchers
    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        documentUri = uri
    }

    val carnetLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        carnetUri = uri
    }

    val selfieLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selfieUri = uri
    }

    val totalSteps = 4

    LaunchedEffect(uiState.isSubmitting) {
        isLoading = uiState.isSubmitting
    }

    LaunchedEffect(uiState.submitted) {
        if (uiState.submitted) {
            manager.navController.navigate(Routes.Login.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    MainLayout(pageLoading = isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { 
                        if (currentStep > 1) {
                            currentStep--
                        } else {
                            manager.navController.popBackStack()
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Verificación",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(totalSteps) { step ->
                    val stepNumber = step + 1
                    val isCompleted = when (stepNumber) {
                        1 -> selectedUniversity != null
                        2 -> documentUri != null
                        3 -> carnetUri != null
                        4 -> selfieUri != null
                        else -> false
                    }
                    val isCurrent = stepNumber == currentStep
                    
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                when {
                                    isCompleted -> MaterialTheme.colorScheme.primary
                                    isCurrent -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stepNumber.toString(),
                            color = when {
                                isCompleted -> MaterialTheme.colorScheme.onPrimary
                                isCurrent -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    
                    if (step < totalSteps - 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Step content
            when (currentStep) {
                1 -> UniversityNameStep(
                    selectedUniversity = selectedUniversity,
                    onUniversitySelected = { selectedUniversity = it },
                    onNext = { if (selectedUniversity != null) currentStep++ }
                )
                2 -> DocumentPhotoStep(
                    documentUri = documentUri,
                    onImageClick = { documentLauncher.launch("image/*") },
                    onNext = { if (documentUri != null) currentStep++ },
                    onBack = { currentStep-- }
                )
                3 -> CarnetPhotoStep(
                    carnetUri = carnetUri,
                    onImageClick = { carnetLauncher.launch("image/*") },
                    onNext = { if (carnetUri != null) currentStep++ },
                    onBack = { currentStep-- }
                )
                4 -> SelfieStep(
                    selfieUri = selfieUri,
                    onImageClick = { selfieLauncher.launch("image/*") },
                    onSubmit = {
                        isLoading = true
                        viewModel.submitVerification()
                    },
                    onBack = { currentStep-- }
                )
            }
        }
    }
}

@Composable
fun UniversityNameStep(
    selectedUniversity: String?,
    onUniversitySelected: (String?) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Paso 1 de 4",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Selecciona tu Universidad",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Selecciona tu universidad de la lista disponible para verificar tu identidad estudiantil.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            DropdownMenuBox(
                label = "Universidad",
                selectedOption = selectedUniversity,
                options = availableUniversities,
                onOptionSelected = onUniversitySelected,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        MainButton(
            text = "Continuar",
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedUniversity != null
        )
    }
}

@Composable
fun DocumentPhotoStep(
    documentUri: Uri?,
    onImageClick: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Paso 2 de 4",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Documento Universitario",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Toma una foto clara del frente de tu documento universitario (carnet estudiantil, credencial universitaria, etc.).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            ImageUploadBox(
                imageUri = documentUri,
                onImageClick = onImageClick,
                placeholderText = "Toca para seleccionar foto del documento"
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MainButton(
                text = "Atrás",
                onClick = onBack,
                modifier = Modifier.weight(1f),
                fillMaxWidth = false
            )
            
            MainButton(
                text = "Continuar",
                onClick = onNext,
                modifier = Modifier.weight(1f),
                enabled = documentUri != null
            )
        }
    }
}

@Composable
fun CarnetPhotoStep(
    carnetUri: Uri?,
    onImageClick: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Paso 3 de 4",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Foto del Documento",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Toma una foto clara de tu documento de identificación (cédula).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            ImageUploadBox(
                imageUri = carnetUri,
                onImageClick = onImageClick,
                placeholderText = "Toca para seleccionar foto de la cédula"
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MainButton(
                text = "Atrás",
                onClick = onBack,
                modifier = Modifier.weight(1f),
                fillMaxWidth = false
            )
            
            MainButton(
                text = "Continuar",
                onClick = onNext,
                modifier = Modifier.weight(1f),
                enabled = carnetUri != null
            )
        }
    }
}

@Composable
fun SelfieStep(
    selfieUri: Uri?,
    onImageClick: () -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    var showTermsDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Paso 4 de 4",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Selfie",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Toma una selfie para verificar tu identidad. Asegúrate de que tu rostro sea claramente visible.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            ImageUploadBox(
                imageUri = selfieUri,
                onImageClick = onImageClick,
                placeholderText = "Toca para tomar selfie"
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MainButton(
                text = "Atrás",
                onClick = onBack,
                modifier = Modifier.weight(1f),
                fillMaxWidth = false
            )
            
            MainButton(
                text = "Verificar",
                onClick = { showTermsDialog = true },
                modifier = Modifier.weight(1f),
                enabled = selfieUri != null
            )
        }
    }
    
    // Terms and Conditions Dialog
    if (showTermsDialog) {
        TermsAndConditionsDialog(
            onAccept = {
                showTermsDialog = false
                onSubmit()
            },
            onReject = {
                showTermsDialog = false
            }
        )
    }
}

@Composable
fun ImageUploadBox(
    imageUri: Uri?,
    onImageClick: () -> Unit,
    placeholderText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = if (imageUri != null) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onImageClick() }
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Overlay with camera icon
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Cambiar imagen",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Toca para cambiar",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Agregar imagen",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = placeholderText,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun TermsAndConditionsDialog(
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onReject,
        title = {
            Text(
                text = "Términos y Condiciones",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Text(
                text = "Al enviar esta información, aceptas que será utilizada únicamente para verificar tu identidad universitaria y habilitar tu cuenta de emprendedor.\n\n" +
                      "• Tus datos serán procesados de forma segura\n" +
                      "• Solo utilizaremos esta información para verificación\n" +
                      "• Puedes solicitar la eliminación de tus datos en cualquier momento\n" +
                      "• No compartiremos tu información con terceros",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        },
        confirmButton = {
            MainButton(
                text = "Acepto",
                onClick = onAccept,
                modifier = Modifier.fillMaxWidth()
            )
        },
        dismissButton = {
            MainButton(
                text = "Rechazo",
                onClick = onReject,
                modifier = Modifier.fillMaxWidth(),
                fillMaxWidth = false
            )
        }
    )
}
