package com.codeoflegends.unimarket.features.entrepreneurship.data.repository

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator
import java.util.UUID

interface CollaboratorRepository {
    suspend fun getCollaborators(entrepreneurshipId: UUID): List<Collaborator>
} 