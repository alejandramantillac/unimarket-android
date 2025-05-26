package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import java.util.UUID
import javax.inject.Inject

class DeleteCollaboratorUseCase @Inject constructor(
    private val repository: CollaboratorRepository
) {
    suspend operator fun invoke(id: UUID) {
        repository.deleteCollaborator(id)
    }
} 