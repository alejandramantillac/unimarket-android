package com.codeoflegends.unimarket.core.utils

import com.codeoflegends.unimarket.config.RetrofitConfig

object DirectusImageUrl {
    /**
     * Convierte una URL de imagen de Directus a una URL completa
     * Si la URL ya es completa (empieza con http), la retorna tal cual
     * Si es una ruta relativa, le agrega el dominio base de Directus
     */
    fun buildFullUrl(imageUrl: String?): String {
        if (imageUrl.isNullOrBlank()) {
            return ""
        }
        
        return when {
            // Si ya es una URL completa, retornarla tal cual
            imageUrl.startsWith("http://") || imageUrl.startsWith("https://") -> imageUrl
            // Si empieza con /, agregar el dominio base
            imageUrl.startsWith("/") -> "${RetrofitConfig.URL}$imageUrl"
            // Si no tiene /, agregar dominio base y /
            else -> "${RetrofitConfig.URL}/$imageUrl"
        }
    }
}

