package com.codeoflegends.unimarket.features.order.data.dto.get

import java.time.LocalDateTime
import java.util.UUID

data class PaymentDto(
    val id: UUID,
    val status: String,
    val paymentDate: String,
    val createdAt: String,
    val paymentEvidences: List<PaymentEvidenceDto>,
    val paymentMethod: PaymentMethodDto,
)