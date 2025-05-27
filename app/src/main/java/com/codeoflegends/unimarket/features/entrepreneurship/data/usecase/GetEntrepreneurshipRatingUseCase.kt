package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.ReviewRating
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import java.util.UUID
import javax.inject.Inject

class GetEntrepreneurshipRatingUseCase @Inject constructor(
    private val repository: EntrepreneurshipReviewRepository
) {
    suspend operator fun invoke(id: UUID): ReviewRating {
        return repository.getEntrepreneurshipRating(id).getOrThrow()
    }
}