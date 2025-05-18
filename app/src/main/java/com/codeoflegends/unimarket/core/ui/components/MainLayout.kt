package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainLayout(
    barOptions: AppBarOptions = AppBarOptions(),
    pageLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    LoadingOverlay(pageLoading) {
        Scaffold(
            topBar = {
                if (barOptions.show) {
                    AppBar(barOptions = barOptions)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    }
}