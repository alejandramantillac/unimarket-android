package com.codeoflegends.unimarket.features.order.data.dto.update

import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentEvidenceDto
import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentMethodDto
import java.util.UUID

class UpdatePaymentDto(
    val id: UUID,
    val status: String,
)