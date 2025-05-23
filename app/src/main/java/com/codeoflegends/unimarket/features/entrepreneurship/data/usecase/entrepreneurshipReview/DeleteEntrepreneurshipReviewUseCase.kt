package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import javax.inject.Inject

class DeleteEntrepreneurshipReviewUseCase @Inject constructor(
    private val repository: EntrepreneurshipReviewRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteEntrepreneurshipReview(id).getOrThrow()
    }
}