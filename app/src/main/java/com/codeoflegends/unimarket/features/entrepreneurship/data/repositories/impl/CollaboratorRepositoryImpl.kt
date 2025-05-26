package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDomain
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.service.CollaboratorApi
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import java.util.UUID
import javax.inject.Inject

class CollaboratorRepositoryImpl @Inject constructor(
    private val api: CollaboratorApi
) : CollaboratorRepository {

    override suspend fun getCollaborators(entrepreneurshipId: UUID): List<Collaborator> {
        return api.getCollaborators(entrepreneurshipId).map { it.toDomain() }
    }

    override suspend fun createCollaborator(collaborator: Collaborator): Collaborator {
        return api.createCollaborator(collaborator.toDto()).toDomain()
    }

    override suspend fun deleteCollaborator(id: UUID) {
        api.deleteCollaborator(id)
    }

    override suspend fun updateCollaborator(id: UUID, collaborator: Collaborator): Collaborator {
        return api.updateCollaborator(id, collaborator.toDto()).toDomain()
    }
} 