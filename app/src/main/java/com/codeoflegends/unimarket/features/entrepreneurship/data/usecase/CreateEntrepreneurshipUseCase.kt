package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import javax.inject.Inject

class CreateEntrepreneurshipUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(entrepreneurship: Entrepreneurship): Entrepreneurship {
        repository.createEntrepreneurship(entrepreneurship).getOrThrow()
        return entrepreneurship
    }
} 