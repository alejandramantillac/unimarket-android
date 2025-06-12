package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import java.util.UUID

interface CollaboratorRepository {
    suspend fun getCollaborators(entrepreneurshipId: UUID): List<Collaborator>

}