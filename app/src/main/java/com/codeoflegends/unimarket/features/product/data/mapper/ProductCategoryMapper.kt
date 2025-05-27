package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.get.CategoryDto
import com.codeoflegends.unimarket.features.product.data.model.ProductCategory

object ProductCategoryMapper {
    fun fromDto(categoryDto: CategoryDto) =
        ProductCategory(
            name = categoryDto.name,
            description = categoryDto.description,
        )
}