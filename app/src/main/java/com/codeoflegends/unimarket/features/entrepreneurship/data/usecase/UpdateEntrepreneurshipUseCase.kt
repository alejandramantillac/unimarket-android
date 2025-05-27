package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import javax.inject.Inject

class UpdateEntrepreneurshipUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(entrepreneurship: Entrepreneurship): Entrepreneurship {
        repository.updateEntrepreneurship(entrepreneurship).getOrThrow()
        return entrepreneurship
    }
}