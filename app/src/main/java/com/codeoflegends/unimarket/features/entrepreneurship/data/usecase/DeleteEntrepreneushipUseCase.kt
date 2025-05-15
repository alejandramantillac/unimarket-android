package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import javax.inject.Inject

class DeleteEntrepreneushipUseCase @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteEntrepreneurship(id).getOrThrow()
    }
}