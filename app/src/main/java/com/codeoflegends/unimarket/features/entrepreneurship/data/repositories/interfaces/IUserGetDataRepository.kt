package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces


import com.codeoflegends.unimarket.core.data.dto.UserDto

interface IUserGetDataRepository {
    suspend fun getUserData(): Result<UserDto>
}