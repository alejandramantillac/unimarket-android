package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainLayout(
    barOptions: AppBarOptions = AppBarOptions(),
    addPadding: Boolean = false,
    pageLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    LoadingOverlay(pageLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Contenido principal
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (addPadding && barOptions.show) {
                            Modifier.padding(top = 56.dp)
                        } else {
                            Modifier
                        }
                    )
            ) {
                content()
            }

            // AppBar por encima del contenido
            if (barOptions.show) {
                AppBar(barOptions = barOptions)
            }
        }
    }
}