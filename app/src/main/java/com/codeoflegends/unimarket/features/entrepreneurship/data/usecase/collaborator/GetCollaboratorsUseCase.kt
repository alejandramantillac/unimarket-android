package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.collaborator

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import java.util.UUID
import javax.inject.Inject

class GetCollaboratorsUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(entrepreneurshipId: UUID): List<Collaborator> {
        return repository.getAllEntrepreneurshipCollaborators(entrepreneurshipId).getOrThrow()
    }
} 