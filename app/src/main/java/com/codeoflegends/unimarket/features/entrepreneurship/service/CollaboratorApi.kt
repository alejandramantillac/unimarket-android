package com.codeoflegends.unimarket.features.entrepreneurship.service

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.CollaboratorDto
import retrofit2.http.*
import java.util.UUID

interface CollaboratorApi {
    @GET("items/collaborators/entrepreneurship/{entrepreneurshipId}")
    suspend fun getCollaborators(@Path("entrepreneurshipId") entrepreneurshipId: UUID): List<CollaboratorDto>


} 