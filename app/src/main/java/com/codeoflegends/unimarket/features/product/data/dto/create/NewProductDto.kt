package com.codeoflegends.unimarket.features.product.data.dto.create

/**
 * Data Transfer Object for API responses when fetching a single product's details
 * Used only at the data layer for API communication
 */
data class NewProductDto(
    val id: String? = null,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val stockAlert: Int,
    val published: Boolean,
    val entrepreneurship: EntrepreneurshipCreateDto,
    val variants: List<NewVariantDto> = emptyList(),
    val specifications: List<NewSpecificationDto> = emptyList(),
)

data class EntrepreneurshipCreateDto(
    val id: String,
)