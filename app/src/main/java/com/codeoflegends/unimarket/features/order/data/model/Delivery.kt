package com.codeoflegends.unimarket.features.order.data.model

import com.codeoflegends.unimarket.features.order.data.dto.get.DeliveryStatusDto
import com.codeoflegends.unimarket.features.order.data.dto.get.PartnerDto
import java.util.UUID

data class Delivery(
    val id : UUID,
    val type: String,
    val deliveryAddress: String,
    val status : DeliveryStatusDto,
)