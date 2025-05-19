package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import javax.inject.Inject

class UpdateEntrepreneurshipUseCase @Inject constructor(
    private val repository: EntrepreneurshipRepository
) {
    suspend operator fun invoke(entrepreneurship: Entrepreneurship): Entrepreneurship {
        repository.updateEntrepreneurship(entrepreneurship).getOrThrow()
        return entrepreneurship
    }
} 