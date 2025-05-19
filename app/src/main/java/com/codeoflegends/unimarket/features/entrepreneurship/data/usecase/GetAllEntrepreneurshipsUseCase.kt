package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import javax.inject.Inject

class GetAllEntrepreneurshipsUseCase @Inject constructor(
    private val repository: EntrepreneurshipRepository
) {
    suspend operator fun invoke(): List<Entrepreneurship> {
        return repository.getAllEntrepreneurships().getOrThrow()
    }
} 