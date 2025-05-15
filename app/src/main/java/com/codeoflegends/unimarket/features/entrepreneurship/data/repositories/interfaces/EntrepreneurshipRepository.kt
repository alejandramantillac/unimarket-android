package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship

interface IEntrepreneurshipRepository {
    suspend fun createEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit>
    suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit>
    suspend fun deleteEntrepreneurship(entrepreneurshipId: String): Result<Unit>
    suspend fun getEntrepreneurship(entrepreneurshipId: String): Result<Entrepreneurship>
    suspend fun getAllEntrepreneurship(): Result<List<Entrepreneurship>>
}

