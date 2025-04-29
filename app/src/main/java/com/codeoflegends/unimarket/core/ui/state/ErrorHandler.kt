package com.codeoflegends.unimarket.core.ui.state

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandler {
    fun handleError(exception: Throwable, defaultMessage: String = "Ha ocurrido un error") {
        val message = when (exception) {
            is ConnectException,
            is UnknownHostException -> "No se pudo conectar al servidor. Verifica tu conexión."

            is SocketTimeoutException -> "La conexión ha tardado demasiado."
            is HttpException -> {
                when (exception.code()) {
                    400 -> "Solicitud incorrecta. Por favor verifica los datos."
                    401 -> "Sesión expirada. Por favor inicia sesión nuevamente."
                    403 -> "No tienes permisos para realizar esta acción."
                    404 -> "El recurso solicitado no se encuentra disponible."
                    500 -> "Error en el servidor. Por favor intenta más tarde."
                    else -> exception.message() ?: defaultMessage
                }
            }

            else -> exception.message ?: defaultMessage
        }

        MessageManager.showMessage(message = message, isError = true, dismissTimeout = 5f)
    }

    fun showSuccess(
        message: String,
        actionLabel: String? = null,
        onAction: (() -> Unit)? = null,
        dismissTimeout: Float = -1f
    ) {
        MessageManager.showMessage(
            message = message,
            isError = false,
            actionLabel = actionLabel,
            onAction = onAction,
            dismissTimeout = dismissTimeout
        )
    }
}