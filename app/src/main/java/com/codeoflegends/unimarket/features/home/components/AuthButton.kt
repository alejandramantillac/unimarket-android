package com.codeoflegends.unimarket.features.home.components



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val purpleColor = Color(0xFF7B2CBF)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = purpleColor,
            contentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text)
    }
}
