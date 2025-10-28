package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import javax.inject.Inject
import java.util.UUID

class DeleteProductReviewUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(reviewId: UUID) {
        repository.deleteReview(reviewId).getOrThrow()
    }
}
