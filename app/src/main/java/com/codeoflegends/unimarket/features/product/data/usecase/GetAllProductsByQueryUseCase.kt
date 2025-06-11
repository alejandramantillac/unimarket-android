package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import java.util.UUID
import javax.inject.Inject

class GetAllProductsByQueryUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        entrepreneurshipId: UUID,
        nameContains: String = "",
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): List<Product> {
        return repository.getAllProductsByQuery(
            entrepreneurshipId = entrepreneurshipId,
            nameContains = nameContains,
            filters = filters,
            limit = limit,
            page = page
        ).getOrThrow()
    }

    suspend operator fun invoke(
        nameContains: String = "",
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): List<Product> {
        return repository.getAllProductsByQuery(
            nameContains = nameContains,
            filters = filters,
            limit = limit,
            page = page
        ).getOrThrow()
    }
}