package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import javax.inject.Inject
import java.util.UUID

class GetEntrepreneurshipUseCase @Inject constructor(
    private val repository: EntrepreneurshipRepository
) {
    suspend operator fun invoke(id: UUID): Entrepreneurship {
        return repository.getEntrepreneurship(id).getOrThrow()
    }
} 