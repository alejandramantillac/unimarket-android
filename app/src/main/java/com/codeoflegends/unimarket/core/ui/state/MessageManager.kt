package com.codeoflegends.unimarket.core.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class MessageData(
    val message: String = "",
    val isError: Boolean = false,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null,
    val dismissTimeout: Float = -1f
)

object MessageManager {
    var currentMessage by mutableStateOf<MessageData?>(null)
        private set

    fun showMessage(
        message: String,
        isError: Boolean = false,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
        dismissTimeout: Float = -1f
    ) {
        currentMessage = MessageData(
            message = message,
            isError = isError,
            actionLabel = actionLabel,
            onAction = onAction,
            dismissTimeout = dismissTimeout
        )
    }

    fun dismissMessage() {
        currentMessage = null
    }
}