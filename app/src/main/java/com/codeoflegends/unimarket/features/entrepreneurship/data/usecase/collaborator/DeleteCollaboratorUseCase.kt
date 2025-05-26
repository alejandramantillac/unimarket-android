package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.collaborator

import com.codeoflegends.unimarket.features.entrepreneurship.data.repository.CollaboratorRepository
import java.util.UUID
import javax.inject.Inject

class DeleteCollaboratorUseCase @Inject constructor(
    private val repository: CollaboratorRepository
) {
    suspend operator fun invoke(id: UUID) {
        repository.deleteCollaborator(id)
    }
} 