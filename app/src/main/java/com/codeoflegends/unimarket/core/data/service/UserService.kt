package com.codeoflegends.unimarket.core.data.service

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.dto.DirectusDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface UserService {
    @GET("/users")
    suspend fun findUserByEmail(
        @Query("filter[email][_eq]") email: String,
        @QueryMap query: Map<String, String> = UserDto.query().build()
    ): DirectusDto<List<UserDto>>
} 