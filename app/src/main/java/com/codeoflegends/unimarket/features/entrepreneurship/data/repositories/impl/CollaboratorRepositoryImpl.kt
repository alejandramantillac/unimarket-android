package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDomain
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.CollaboratorApi
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollaboratorRepositoryImpl @Inject constructor(
    private val api: CollaboratorApi
) : CollaboratorRepository {
    override suspend fun getCollaborators(entrepreneurshipId: UUID): List<Collaborator> {
        return api.getCollaborators(entrepreneurshipId).map { it.toDomain() }
    }
}
