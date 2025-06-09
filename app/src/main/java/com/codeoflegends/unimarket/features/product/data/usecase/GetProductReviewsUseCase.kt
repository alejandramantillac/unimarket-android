package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.model.Review
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import java.util.UUID
import javax.inject.Inject

class GetProductReviewsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: UUID, page: Int, limit: Int): List<Review> {
        return repository.getProductReviews(productId, page, limit).getOrThrow()
    }
} 