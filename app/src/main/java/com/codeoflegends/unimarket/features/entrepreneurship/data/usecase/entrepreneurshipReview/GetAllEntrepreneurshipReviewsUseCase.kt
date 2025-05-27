package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import java.util.UUID
import javax.inject.Inject

class GetAllEntrepreneurshipReviewsUseCase @Inject constructor(
    private val repository: EntrepreneurshipReviewRepository
) {
    suspend operator fun invoke(entrepreneurshipId: UUID, page: Int, limit: Int): List<EntrepreneurshipReview> {
        return repository.getEntrepreneurshipReviews(
            entrepreneurshipId = entrepreneurshipId,
            page = page,
            limit = limit
        ).getOrThrow()
    }
}