package com.codeoflegends.unimarket.features.entrepreneurship.service



import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.dto.DirectusDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UserGetDataService {
    @GET("/users/me")
    suspend fun getUserData(
        @QueryMap query: Map<String, String> = UserDto.query().build()
    ): DirectusDto<UserDto>
}