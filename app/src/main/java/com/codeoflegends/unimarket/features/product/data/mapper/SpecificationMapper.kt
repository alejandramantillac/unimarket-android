package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.SpecificationDto
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification

object SpecificationMapper {

    fun mapToDto(specification: ProductSpecification) =
        SpecificationDto(
            id = specification.id,
            key = specification.key,
            value = specification.value,
        )

    fun mapFromDto(specificationDto: SpecificationDto) =
        ProductSpecification(
            id = specificationDto.id,
            key = specificationDto.key,
            value = specificationDto.value,
        )
}
