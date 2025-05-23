package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipWithFounder
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import java.util.UUID
import javax.inject.Inject

class GetEntrepreneurshipWithFounderUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(id: UUID): EntrepreneurshipWithFounder {
        return repository.getEntrepreneurshipWithFounder(id).getOrThrow()
    }
}
