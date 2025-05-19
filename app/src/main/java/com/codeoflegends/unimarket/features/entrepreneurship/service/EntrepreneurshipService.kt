package com.codeoflegends.unimarket.features.entrepreneurship.service

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface EntrepreneurshipService {
    @GET("/items/Entrepreneurship/{entrepreneurshipId}")
    suspend fun getEntrepreneurship(
        @Path("entrepreneurshipId") entrepreneurshipId: String,
        @QueryMap query: Map<String, String>
    ): DirectusDto<EntrepreneurshipDto>
    //suspend fun getEntrepreneurshipList(): List<Entrepreneurship>
    //suspend fun createEntrepreneurship(entrepreneurship: Entrepreneurship): Entrepreneurship
    //suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Entrepreneurship
    //suspend fun deleteEntrepreneurship(id: String): Boolean
}