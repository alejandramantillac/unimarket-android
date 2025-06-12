package com.codeoflegends.unimarket.features.product.data.dto.get

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

/**
 * Data Transfer Object for API responses when fetching a single product's details
 * Used only at the data layer for API communication
 */
data class ProductDetailDto(
    val id: UUID,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val discount: Double,
    val stockAlert: Int,
    val published: Boolean,
    val entrepreneurship: EntrepreneurshipDto,
    val variants: List<VariantDto> = emptyList(),
    val specifications: List<SpecificationDto> = emptyList(),
) {
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("entrepreneurship")
                .join("specifications")
                .join("variants")
                .join("variants.variant_images")
        }
    }
} 