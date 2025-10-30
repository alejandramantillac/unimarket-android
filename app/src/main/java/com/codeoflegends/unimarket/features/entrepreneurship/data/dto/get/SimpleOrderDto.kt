package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.features.order.data.dto.get.OrderStatusDto
import java.util.UUID

/**
 * DTO simplificado de Order para usar en Entrepreneurship
 * Solo incluye el ID y el status para evitar cargar demasiada información
 */
data class SimpleOrderDto(
    val id: UUID?,
    val status: OrderStatusDto?
)

