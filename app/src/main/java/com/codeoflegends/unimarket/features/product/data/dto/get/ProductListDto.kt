package com.codeoflegends.unimarket.features.product.data.dto.get

import android.util.Log
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
    val discount: Double,
    val stockAlert: Int,
    val published: Boolean,
    val entrepreneurship: EntrepreneurshipDto,
    val image: String? = null
) {
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("entrepreneurship")
        }

        fun queryByEntrepreneurship(
            entrepreneurshipId: String,
            nameContains: String = "",
            filters: List<DirectusQuery.Filter>,
            limit: Int,
            page: Int
        ): DirectusQuery {
            val query = DirectusQuery()
                .paginate(page = page, limit = limit)
                .filter("entrepreneurship", "eq", entrepreneurshipId)
                .join("entrepreneurship")

            if (nameContains.isNotEmpty()) {
                query.filter("name", "icontains", nameContains)
            }

            filters.forEach { filter ->
                query.filter(filter)
            }

            Log.d("ProductListDto", "Query: ${query.build()}")

            return query
        }

        fun queryByFilters(
            nameContains: String = "",
            filters: List<DirectusQuery.Filter>,
            limit: Int,
            page: Int
        ): DirectusQuery {
            val query = DirectusQuery()
                .paginate(page = page, limit = limit)
                .join("entrepreneurship")

            if (nameContains.isNotEmpty()) {
                query.filter("name", "icontains", nameContains)
            }

            filters.forEach { filter ->
                query.filter(filter)
            }

            Log.d("ProductListDto", "Query: ${query.build()}")

            return query
        }
    }
}