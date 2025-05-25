package com.codeoflegends.unimarket.features.order.data.dto.get

import java.time.LocalDateTime
import java.util.UUID


data class PaymentEvidenceDto(
    val id: UUID,
    val uploadedAt: LocalDateTime,
    val description: String,
    val url: String,
)
