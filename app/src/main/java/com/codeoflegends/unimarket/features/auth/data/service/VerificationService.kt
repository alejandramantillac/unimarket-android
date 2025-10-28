package com.codeoflegends.unimarket.features.auth.data.service

import com.codeoflegends.unimarket.core.dto.DirectusDto
import retrofit2.http.Body
import retrofit2.http.POST

data class VerificationRequestDto(
    val id: String
)

data class VerificationResponseDto(
    val id: String?
)

interface VerificationService {
    @POST("/items/Verification")
    suspend fun sendVerification(
        @Body body: VerificationRequestDto
    ): retrofit2.Response<Any>
}


