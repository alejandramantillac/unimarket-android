package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipMapper
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipService
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntrepreneurshipRepositoryDirectus @Inject constructor(
    private val entrepreneurshipService: EntrepreneurshipService
): EntrepreneurshipRepository {
    override suspend fun createEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntrepreneurship(entrepreneurshipId: UUID): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getEntrepreneurship(entrepreneurshipId: UUID): Result<Entrepreneurship> = try{
        val entrepreneurshipDto = entrepreneurshipService.getEntrepreneurship(
            entrepreneurshipId.toString(),
            EntrepreneurshipDto.query().build()
        ).data
        val entrepreneurship = EntrepreneurshipMapper.entrepreneurshipDtoToEntrepreneurship(entrepreneurshipDto)
        Result.success(entrepreneurship)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllEntrepreneurships(): Result<List<Entrepreneurship>> {
        TODO("Not yet implemented")
    }
}