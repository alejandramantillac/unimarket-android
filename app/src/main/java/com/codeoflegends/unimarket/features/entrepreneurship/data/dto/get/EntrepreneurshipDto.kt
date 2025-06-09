package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductDto
import com.codeoflegends.unimarket.features.product.data.model.Product
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
    //val orders: List<UUID>,
    val socialNetworks: List<Map<String, String>>,
    val tags: List<TagRelationDto>,
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
        }
    }
}