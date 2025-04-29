package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex

@Composable
fun AppContentWrapper(
    content: @Composable () -> Unit,
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            // Main content
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                content()
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxSize()
                    .zIndex(2f)
            ) {
                GlobalMessageHost()
            }
        }
    }
}