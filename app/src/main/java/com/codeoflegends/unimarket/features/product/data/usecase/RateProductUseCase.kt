package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import javax.inject.Inject
import java.util.UUID

class RateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: UUID, rating: Int, comment: String) {
        repository.createReview(productId, rating, comment).getOrThrow()
    }
} 