package com.codeoflegends.unimarket.features.product.data.dto

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

/**
 * Data Transfer Object for API responses when fetching product lists
 * Used only at the data layer for API communication
 */
data class ProductListDto(
    val id: UUID,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val stockAlert: Int,
    val published: Boolean,
    val entrepreneurship: EntrepreneurshipDto
) {
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("entrepreneurship")
        }
    }
}