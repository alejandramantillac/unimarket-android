package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repository: IUserGetDataRepository
) {
    suspend operator fun invoke(): UserDto {
        return repository.getUserData().getOrThrow()
    }
}