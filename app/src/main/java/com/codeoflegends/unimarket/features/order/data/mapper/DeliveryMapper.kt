package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.features.order.data.dto.get.DeliveryDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdateDeliveryDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdateDeliveryStatusDto
import com.codeoflegends.unimarket.features.order.data.model.Delivery

object DeliveryMapper {

    fun mapFromDto(deliveryDto: DeliveryDto): Delivery {
        return Delivery(
            id = deliveryDto.id,
            type = deliveryDto.type,
            deliveryAddress = deliveryDto.deliveryAddress,
            status = deliveryDto.status
        )
    }

    fun toUpdateDeliveryDto(delivery: Delivery) =
        UpdateDeliveryDto(
            id = delivery.id,
            status = UpdateDeliveryStatusDto(
                id = delivery.status.id,
                name = delivery.status.name
            )
        )
}