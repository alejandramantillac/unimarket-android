package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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