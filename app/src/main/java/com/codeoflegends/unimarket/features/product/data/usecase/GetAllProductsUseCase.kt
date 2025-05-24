package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import java.util.UUID
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(entrepreneurshipId: UUID? = null,
                                nameContains: String = "",
                                page: Int = 1,
                                limit: Int = 10
    ): List<Product> {
        return if (entrepreneurshipId != null) {
            repository.getAllProductsByEntrepreneurship(
                entrepreneurshipId = entrepreneurshipId,
                nameContains = nameContains,
                limit = limit,
                page = page
            ).getOrThrow()
        } else {
            repository.getAllProducts().getOrThrow()
        }
    }
} 