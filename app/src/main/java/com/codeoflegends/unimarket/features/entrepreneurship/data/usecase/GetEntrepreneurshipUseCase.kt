package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import java.util.UUID
import javax.inject.Inject

class GetEntrepreneurshipUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(id: UUID): Entrepreneurship {
        return repository.getEntrepreneurship(id).getOrThrow()
    }
}