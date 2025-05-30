package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.create.NewSpecificationDto
import com.codeoflegends.unimarket.features.product.data.dto.get.SpecificationDto
import com.codeoflegends.unimarket.features.product.data.dto.update.UpdateSpecificationDto
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification

object SpecificationMapper {

    fun mapFromDto(specificationDto: SpecificationDto) =
        ProductSpecification(
            id = specificationDto.id,
            key = specificationDto.key,
            value = specificationDto.value,
        )

    fun toNewSpecificationDto(specification: ProductSpecification) =
        NewSpecificationDto(
            key = specification.key,
            value = specification.value
        )

    fun toUpdateSpecificationDto(specification: ProductSpecification) =
        UpdateSpecificationDto(
            id = specification.id,
            key = specification.key,
            value = specification.value
        )
}
