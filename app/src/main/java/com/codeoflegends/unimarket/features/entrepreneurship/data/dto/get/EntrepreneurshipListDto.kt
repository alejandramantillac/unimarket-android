package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class EntrepreneurshipListDto(
    val id: UUID?,
    val name: String,
    val slogan: String,
    val description: String,
    val creationDate: String,
    val customization: EntrepreneurshipCustomizationDto,
    val email: String,
    val phone: String,
    val category: Int,
    val socialNetworks: List<Map<String, String>>,
    val tags: List<TagRelationDto>,
) {
    companion object {

        fun queryByFilters(
            nameContains: String = "",
            filters: List<DirectusQuery.Filter>,
            limit: Int,
            page: Int
        ): DirectusQuery {
            val query = DirectusQuery()
                .join("customization")
                .join("tags.Tags_id")
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