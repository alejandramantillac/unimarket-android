package com.codeoflegends.unimarket.features.order.data.dto.get

import java.util.UUID

class DeliveryDto (
    val id : UUID,
    val type: String,
    val deliveryAddress: String,
    val status : DeliveryStatusDto,
    val partner: PartnerDto,
)