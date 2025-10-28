package com.codeoflegends.unimarket.features.auth.data.repositories.impl

import com.codeoflegends.unimarket.core.ui.state.ErrorHandler
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.VerificationRepository
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import com.codeoflegends.unimarket.features.auth.data.service.JwtDecoder
import com.codeoflegends.unimarket.features.auth.data.service.VerificationRequestDto
import com.codeoflegends.unimarket.features.auth.data.service.VerificationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerificationRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val jwtDecoder: JwtDecoder,
    private val verificationService: VerificationService
) : VerificationRepository {
    override suspend fun sendVerification(): Result<Unit> {
        return try {
            val token = tokenRepository.getAccessToken()
            val payload = token?.let { jwtDecoder.decodePayload(it) }
                ?: return Result.failure(IllegalStateException("Token inválido"))

            val request = VerificationRequestDto(id = payload.userId)
            val response = verificationService.sendVerification(request)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception("Error ${response.code()}: ${response.message()} - $errorBody"))
            }
        } catch (e: Exception) {
            ErrorHandler.handleError(e)
            Result.failure(e)
        }
    }
}


