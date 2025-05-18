package com.codeoflegends.unimarket.features.product.data.dto

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

data class SimpleProduct(
    val id: UUID,
    val category: String,
    val name: String,
    val description: String,
    val price: Double,
    val stockAlert: Int,
    val published: Boolean,
    val entrepreneurship: SimpleEntrepreneurship,
){
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("entrepreneurship")
        }

        fun fromProduct(product: Product): SimpleProduct {
            return SimpleProduct(
                id = product.id!!,
                category = product.category.name,
                name = product.name,
                description = product.description,
                price = product.price,
                stockAlert = product.stockAlert,
                published = product.published,
                entrepreneurship = SimpleEntrepreneurship(
                    id = product.entrepreneurship.id.toString(),
                    name = product.entrepreneurship.name
                )
            )
        }
    }
}

data class SimpleEntrepreneurship(
    val id: String,
    val name: String
)

