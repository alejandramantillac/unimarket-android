package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import dagger.internal.InjectedFieldSignature
import java.util.UUID
import javax.inject.Inject

class CreateEntrepreneurshipReviewUseCase @Inject constructor(
    private val repository: EntrepreneurshipReviewRepository
) {
    suspend operator fun invoke(
        entrepreneurship: UUID,
        rating: Int,
        comment: String
    ) {
        repository.createEntrepreneurshipReview(
            entrepreneurship = entrepreneurship,
            rating = rating,
            comment = comment
        ).getOrThrow()
    }
}