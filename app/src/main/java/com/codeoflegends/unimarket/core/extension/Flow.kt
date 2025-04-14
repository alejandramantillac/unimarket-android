package com.codeoflegends.unimarket.core.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun <T> SharedFlow<T>.CollectAsEventEffect(onEvent: (T) -> Unit) {
    val flow = this
    LaunchedEffect(flow) {
        flow.collect { event ->
            onEvent(event)
        }
    }
}