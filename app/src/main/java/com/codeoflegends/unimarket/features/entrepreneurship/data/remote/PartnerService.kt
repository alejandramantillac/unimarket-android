package com.codeoflegends.unimarket.features.entrepreneurship.data.remote

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.PartnerDto
import retrofit2.http.*
import java.util.UUID

interface PartnerService {
    @GET("/items/Partner")
    suspend fun getPartnersByEntrepreneurship(
        @Query("filter[entrepreneurship][_eq]") entrepreneurshipId: UUID,
        @Query("fields") fields: String = "*"
    ): DirectusDto<List<PartnerDto>>

    @GET("/items/Partner/{id}")
    suspend fun getPartnerById(
        @Path("id") id: UUID,
        @Query("fields") fields: String = "*"
    ): DirectusDto<PartnerDto>

    @POST("/items/Partner")
    suspend fun createPartner(
        @Body partner: PartnerDto
    ): DirectusDto<PartnerDto>

    @PATCH("/items/Partner/{id}")
    suspend fun updatePartner(
        @Path("id") id: UUID,
        @Body partner: PartnerDto
    ): DirectusDto<PartnerDto>

    @DELETE("/items/Partner/{id}")
    suspend fun deletePartner(
        @Path("id") id: UUID
    )
} 