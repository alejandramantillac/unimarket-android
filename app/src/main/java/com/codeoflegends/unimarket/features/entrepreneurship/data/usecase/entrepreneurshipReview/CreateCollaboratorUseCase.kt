package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import javax.inject.Inject

class CreateCollaboratorUseCase @Inject constructor(
    private val repository: CollaboratorRepository
) {
    suspend operator fun invoke(collaborator: Collaborator): Collaborator {
        return repository.createCollaborator(collaborator)
    }
} 