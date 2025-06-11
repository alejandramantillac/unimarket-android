package com.codeoflegends.unimarket.features.order.data.mapper

import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDetailDto
import com.codeoflegends.unimarket.features.order.data.model.OrderDetail
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant

object OrderDetailsMapper {

    fun mapFromDto(detailDto: OrderDetailDto): OrderDetail {
        return OrderDetail(
            id = detailDto.id,
            amount = detailDto.amount,
            unitPrice = detailDto.unitPrice,
            productVariant = ProductVariant(
                id = detailDto.productVariant.id,
                name = detailDto.productVariant.name,
                stock = detailDto.productVariant.stock,
                variantImages = detailDto.productVariant.variantImages.map { it.imageUrl }
            )
        )
    }
}
