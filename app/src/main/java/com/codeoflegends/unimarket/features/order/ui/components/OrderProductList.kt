package com.codeoflegends.unimarket.features.order.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant

@Composable
fun OrderProductList(
    products: List<ProductVariant>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
       /* Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            products.forEachIndexed { index, product ->
                OrderProductItem(
                    product = product,
                    modifier = Modifier.fillMaxWidth()
                )
                if (index < products.size - 1) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray
                    )
                }
            }
        }*/
    }
}