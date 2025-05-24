package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipCategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: EntrepreneurshipCategoryRepository
) {
    suspend operator fun invoke(): List<EntrepreneurshipCategory> {
        return repository.getEntrepreneurshipCategories(
        ).getOrThrow()
    }
}