package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentDto
import com.codeoflegends.unimarket.features.order.data.dto.get.PaymentMethodDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdateOrderDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdatePaymentDto
import com.codeoflegends.unimarket.features.order.data.model.Payment
import com.codeoflegends.unimarket.features.order.data.model.PaymentEvidence
import com.codeoflegends.unimarket.features.order.data.model.PaymentMethod

object PaymentsMapper {

    fun mapFromDto(paymentDto: PaymentDto): Payment {
        return Payment(
            id = paymentDto.id,
            paymentMethod = PaymentMethod(
                id = paymentDto.paymentMethod.id,
                name = paymentDto.paymentMethod.name
            ),
            paymentEvidences = paymentDto.paymentEvidences.map { evidence ->
                PaymentEvidence(
                    id = evidence.id,
                    url = evidence.url
                )
            }
        )
    }

    fun toUpdatePaymentDto(payment: Payment) {
        UpdatePaymentDto(
            id = payment.id,
            status = "default_status",
        )
    }
}