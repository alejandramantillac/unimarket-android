package com.codeoflegends.unimarket.features.order.data.model

import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentEvidenceDto
import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentMethodDto
import java.util.UUID

class Payment (
    val id: UUID,
    val paymentEvidences: List<PaymentEvidence>,
    val paymentMethod: PaymentMethod,
)