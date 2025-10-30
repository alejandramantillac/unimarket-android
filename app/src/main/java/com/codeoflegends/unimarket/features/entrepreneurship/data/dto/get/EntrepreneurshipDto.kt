package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import android.util.Log
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductDto
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDto
import java.util.UUID

data class EntrepreneurshipDto(
    val id: UUID?,
    val name: String,
    val slogan: String,
    val description: String,
    val creationDate: String,
    val customization: EntrepreneurshipCustomizationDto,
    val email: String,
    val phone: String,
    //val subscription: UUID,
    val category: Int,
    val userFounder: UUID,
    val deletedAt: String?,
    val partners: List<EntrepreneurshipPartnerDto>,
    val products: List<ProductDto>,
    //val collaborations: List<UUID>,
    val orders: List<SimpleOrderDto> = emptyList(),
    val socialNetworks: List<Map<String, String>>,
    val tags: List<TagRelationDto>,
    val reviews: List<EntrepreneurshipReviewDto> = emptyList(),
) {
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("customization")
                .join("tags.Tags_id")
                .join("partners")
                .join("partners.user")
                .join("partners.user.profile")
                .join("products")
                .join("orders.status")
                .join("reviews")
                .join("reviews.user_created")
                .join("reviews.user_created.profile")
        }


        fun queryByFilters(
            nameContains: String = "",
            filters: List<DirectusQuery.Filter>,
            limit: Int,
            page: Int
        ): DirectusQuery {
            val query = query()
                .paginate(page = page, limit = limit)

            if (nameContains.isNotEmpty()) {
                query.filter("name", "icontains", nameContains)
            }

            filters.forEach { filter ->
                query.filter(filter)
            }

            return query
        }
    }
}