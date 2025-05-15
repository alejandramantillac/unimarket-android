package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl.EntrepreneurshipImpl
import javax.inject.Inject

class GetAllEntrepreneuship @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(): List<Entrepreneurship> {
        val repoImpl = repository as EntrepreneurshipImpl
        return repoImpl.getAllEntrepreneurship().getOrThrow()
    }
}