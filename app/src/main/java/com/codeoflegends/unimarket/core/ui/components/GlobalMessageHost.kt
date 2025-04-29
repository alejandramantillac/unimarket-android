package com.codeoflegends.unimarket.core.ui.components

import androidx.compose.runtime.Composable
import com.codeoflegends.unimarket.core.ui.state.MessageManager

@Composable
fun GlobalMessageHost() {
    val messageData = MessageManager.currentMessage

    messageData?.let { data ->
        MessageSnackbar(
            message = data.message,
            isError = data.isError,
            actionLabel = data.actionLabel,
            onAction = {
                data.onAction?.invoke()
                MessageManager.dismissMessage()
            },
            onDismiss = { MessageManager.dismissMessage() },
            dismissTimeSeconds = data.dismissTimeout
        )
    }
}