package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.UserGetDataService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserDataImpl @Inject constructor(
    private val  getUserData: UserGetDataService,
    private val authRepository: AuthRepository
): IUserGetDataRepository {
    override suspend fun getUserData(): Result<UserDto> {
        return try {
            val response = getUserData.getUserData()
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}