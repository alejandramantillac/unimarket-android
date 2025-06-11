package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import java.util.UUID
import javax.inject.Inject

class GetOwnEntrepreneurshipReviewUseCase @Inject constructor(
    private val repository: EntrepreneurshipReviewRepository
) {
    suspend operator fun invoke(entrepreneurshipId: UUID, authorId: UUID): EntrepreneurshipReview? {
        return repository.getOwnEntrepreneurshipReview(
            entrepreneurshipId = entrepreneurshipId,
            authorId = authorId,
        ).getOrThrow()
    }
}