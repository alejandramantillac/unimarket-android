package com.codeoflegends.unimarket.features.entrepreneurship.service

import com.codeoflegends.unimarket.core.dto.DeleteDto
import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.DirectusCollaboratorDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipListDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update.UpdateEntrepreneurshipDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.util.UUID

interface EntrepreneurshipService {
    @GET("/items/Entrepreneurship/{entrepreneurshipId}")
    suspend fun getEntrepreneurship(
        @Path("entrepreneurshipId") entrepreneurshipId: String,
        @QueryMap query: Map<String, String>
    ): DirectusDto<EntrepreneurshipDto>

    @GET("/items/Entrepreneurship/{entrepreneurshipId}")
    suspend fun getEntrepreneurshipCollaborators(
        @Path("entrepreneurshipId") entrepreneurshipId: String,
        @QueryMap query: Map<String, String>
    ): DirectusDto<DirectusCollaboratorDto>

    @POST("/items/Entrepreneurship")
    suspend fun createEntrepreneurship(
        @Body entrepreneurship: NewEntrepreneurshipDto
    )

    @PATCH("/items/Entrepreneurship/{entrepreneurshipId}")
    suspend fun updateEntrepreneurship(
        @Path("entrepreneurshipId") entrepreneurshipId: UUID,
        @Body entrepreneurship: UpdateEntrepreneurshipDto
    )

    @PATCH("/items/Entrepreneurship/{entrepreneurshipId}")
    suspend fun deleteEntrepreneurship(
        @Path("entrepreneurshipId") entrepreneurshipId: UUID,
        @Body delete: DeleteDto
    )

    @GET("/items/Entrepreneurship")
    suspend fun getMyEntrepreneurships(
        @QueryMap query: Map<String, String>
    ): DirectusDto<List<EntrepreneurshipDto>>

    @GET("/items/Entrepreneurship")
    suspend fun getEntrepreneurships(
        @QueryMap query: Map<String, String>
    ): DirectusDto<List<EntrepreneurshipListDto>>
}