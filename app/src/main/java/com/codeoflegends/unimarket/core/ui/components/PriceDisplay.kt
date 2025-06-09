package com.codeoflegends.unimarket.core.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PriceDisplay(
    price: Double,
    textColor: Color? = null,
    discount: Double? = null,
    currency: String = "COP",
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    numberFormat.currency = java.util.Currency.getInstance(currency)
    Log.d("PriceDisplay", "Discount: $discount, Price: $price, Formatted Price: ${numberFormat.format(price)}")
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (discount != null && discount > 0) {
            Column {
                Text(
                    text = numberFormat.format(price),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = numberFormat.format(price * (1 - discount)),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Text(
                text = numberFormat.format(price),
                style = MaterialTheme.typography.titleMedium,
                color = textColor ?: MaterialTheme.colorScheme.onSurface
            )
        }
    }
} 