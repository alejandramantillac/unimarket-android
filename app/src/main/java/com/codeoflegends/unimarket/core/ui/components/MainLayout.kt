package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainLayout(
    barOptions: AppBarOptions = AppBarOptions(),
    pageLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    LoadingOverlay(pageLoading) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (barOptions.show) {
                AppBar(barOptions = barOptions)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}