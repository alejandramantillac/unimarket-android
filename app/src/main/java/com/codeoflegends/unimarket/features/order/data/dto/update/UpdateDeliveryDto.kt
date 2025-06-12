package com.codeoflegends.unimarket.features.order.data.dto.update

import java.util.UUID

class UpdateDeliveryDto(
    val id : UUID,
    val status : UpdateDeliveryStatusDto,
)