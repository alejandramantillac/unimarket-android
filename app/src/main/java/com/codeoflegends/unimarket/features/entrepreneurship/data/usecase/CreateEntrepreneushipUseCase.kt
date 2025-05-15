package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import javax.inject.Inject

class CreateEntrepreneushipUseCase @Inject constructor(
    private val  repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(entrepreneurship: Entrepreneurship): Entrepreneurship {
        repository.createEntrepreneurship(entrepreneurship).getOrThrow()
        return entrepreneurship
    }
}