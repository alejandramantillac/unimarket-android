package com.codeoflegends.unimarket.features.product.data.dto.update

import java.util.UUID

/**
 * Data Transfer Object for API responses when fetching a single product's details
 * Used only at the data layer for API communication
 */
data class UpdateProductDto(
    val id: UUID,
    val category: String?,
    val name: String?,
    val description: String?,
    val price: Double?,
    val stockAlert: Int?,
    val published: Boolean?,
    val variants: List<UpdateVariantDto>?,
    val specifications: List<UpdateSpecificationDto>?,
)