package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.get.TagDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class EntrepreneurshipDto(
    val id: UUID?,
    val name: String,
    val slogan: String,
    val description: String,
    val creation_date: String,
    val customization: EntrepreneurshipCustomizationDto,
    val email: String,
    val phone: String,
    //val subscription: UUID,
    val status: String,
    val category: Int,
    val user_founder: UUID,
    val deleted_at: String?,
    //val partners: List<UUID>,
    //val products: List<UUID>,
    //val collaborations: List<UUID>,
    //val orders: List<UUID>,
    val social_networks: List<Map<String, String>>,
    val tags: List<Int>,
) {
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("customization")
        }
    }
}