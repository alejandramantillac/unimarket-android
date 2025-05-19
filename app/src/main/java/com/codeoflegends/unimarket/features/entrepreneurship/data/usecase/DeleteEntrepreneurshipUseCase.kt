package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import javax.inject.Inject
import java.util.UUID

class DeleteEntrepreneurshipUseCase @Inject constructor(
    private val repository: EntrepreneurshipRepository
) {
    suspend operator fun invoke(id: UUID) {
        repository.deleteEntrepreneurship(id).getOrThrow()
    }
} 