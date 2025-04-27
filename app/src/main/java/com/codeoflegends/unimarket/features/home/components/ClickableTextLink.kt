package com.codeoflegends.unimarket.features.home.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClickableTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    alignEnd: Boolean = false
) {
    val purpleColor = Color(0xFF7B2CBF)

    Text(
        text = text,
        color = purpleColor,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .then(
                if (alignEnd) Modifier.padding(top = 4.dp, bottom = 24.dp)
                else Modifier.padding(top = 8.dp)
            )
            .clickable { onClick() }
    )
}
