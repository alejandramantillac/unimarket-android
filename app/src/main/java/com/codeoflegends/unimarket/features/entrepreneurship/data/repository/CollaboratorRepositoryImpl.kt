package com.codeoflegends.unimarket.features.entrepreneurship.data.repository

import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDomain
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import com.codeoflegends.unimarket.features.entrepreneurship.data.remote.CollaboratorApi
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.CollaboratorRepository
import java.util.UUID
import javax.inject.Inject

class CollaboratorRepositoryImpl @Inject constructor(
    private val api: CollaboratorApi
) : CollaboratorRepository {

    override suspend fun getCollaborators(entrepreneurshipId: UUID): List<Collaborator> {
        return api.getCollaborators(entrepreneurshipId).map { it.toDomain() }
    }
} 