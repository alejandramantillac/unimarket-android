package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.get.VariantDto
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.dto.create.NewVariantDto
import com.codeoflegends.unimarket.features.product.data.dto.create.NewVariantImageDto
import com.codeoflegends.unimarket.features.product.data.dto.update.UpdateVariantDto
import com.codeoflegends.unimarket.features.product.data.dto.update.UpdateVariantImageDto
import com.codeoflegends.unimarket.features.product.data.model.VariantImage

object VariantMapper {

    fun mapFromDto(variantDto: VariantDto) =
        ProductVariant(
            id = variantDto.id,
            name = variantDto.name,
            stock = variantDto.stock,
            variantImages = variantDto.variantImages.map { VariantImage(it.id, it.imageUrl) }
        )

    fun toNewVariantDto(variant: ProductVariant) =
        NewVariantDto(
            name = variant.name,
            stock = variant.stock,
            variantImages = variant.variantImages.map { NewVariantImageDto(it.imageUrl) }
        )

    fun toUpdateVariantDto(variant: ProductVariant) =
        UpdateVariantDto(
            id = variant.id,
            name = variant.name,
            stock = variant.stock,
            variantImages = variant.variantImages.map { UpdateVariantImageDto(it.id, it.imageUrl) }
        )
}