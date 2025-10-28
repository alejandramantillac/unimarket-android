package com.codeoflegends.unimarket.features.auth.data.repositories.interfaces

interface VerificationRepository {
    suspend fun sendVerification(): Result<Unit>
}


