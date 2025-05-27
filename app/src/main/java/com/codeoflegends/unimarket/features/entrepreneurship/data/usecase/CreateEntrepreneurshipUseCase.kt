package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCreate
import javax.inject.Inject

class CreateEntrepreneurshipUseCase @Inject constructor(
    private val  repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(entrepreneurship: EntrepreneurshipCreate): EntrepreneurshipCreate {
        repository.createEntrepreneurship(entrepreneurship).getOrThrow()
        return entrepreneurship
    }
}