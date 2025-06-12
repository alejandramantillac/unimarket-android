package com.codeoflegends.unimarket.features.entrepreneurship.service

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.CollaboratorDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.UUID

interface CollaboratorApi {

    @GET("/items/Collaborator")
    suspend fun getCollaborators(
        @Query("filter[entrepreneurship_id][eq]") entrepreneurshipId: UUID
    ): List<CollaboratorDto>

}