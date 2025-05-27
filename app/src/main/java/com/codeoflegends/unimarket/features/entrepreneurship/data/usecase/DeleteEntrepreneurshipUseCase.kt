package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import java.util.UUID
import javax.inject.Inject

class DeleteEntrepreneurshipUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(id: UUID) {
        repository.deleteEntrepreneurship(id).getOrThrow()
    }
}