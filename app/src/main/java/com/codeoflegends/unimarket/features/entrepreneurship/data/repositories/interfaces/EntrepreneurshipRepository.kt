package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import java.util.UUID

interface IEntrepreneurshipRepository {
    suspend fun createEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit>
    suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit>
    suspend fun deleteEntrepreneurship(entrepreneurshipId: UUID): Result<Unit>
    suspend fun getEntrepreneurship(entrepreneurshipId: UUID): Result<Entrepreneurship>
    suspend fun getAllEntrepreneurships(): Result<List<Entrepreneurship>>
}