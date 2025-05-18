package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.VariantDto
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant

object VariantMapper {
    fun mapToDto(variant: ProductVariant) =
        VariantDto(
            id = variant.id,
            name = variant.name,
            stock = variant.stock,
            variantImages = variant.variantImages
        )

    fun mapFromDto(variantDto: VariantDto) =
        ProductVariant(
            id = variantDto.id,
            name = variantDto.name,
            stock = variantDto.stock,
            variantImages = variantDto.variantImages
        )
}