package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.core.dto.DeleteDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipMapper
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipWithFounder
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipService
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntrepreneurshipRepositoryDirectus @Inject constructor(
    private val entrepreneurshipService: EntrepreneurshipService
): IEntrepreneurshipRepository {
    override suspend fun createEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> = try {
        entrepreneurshipService.createEntrepreneurship(
            EntrepreneurshipMapper.toNewEntrepreneurshipDto(entrepreneurship)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> = try {
        entrepreneurshipService.updateEntrepreneurship(
            entrepreneurship.id!!,
            EntrepreneurshipMapper.toUpdateEntrepreneurshipDto(entrepreneurship)
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteEntrepreneurship(entrepreneurshipId: UUID): Result<Unit>  = try {
        val isoTimestamp = Instant.now().atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT)

        entrepreneurshipService.deleteEntrepreneurship(
            entrepreneurshipId,
            DeleteDto(isoTimestamp)
        )

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
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

    override suspend fun getAllEntrepreneurships(): Result<List<Entrepreneurship>> = try {
        Result.success(emptyList())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getEntrepreneurshipWithFounder(entrepreneurshipId: UUID): Result<EntrepreneurshipWithFounder> = try {
        val entrepreneurshipDto = entrepreneurshipService.getEntrepreneurship(
            entrepreneurshipId.toString(),
            EntrepreneurshipDto.query().build()
        ).data
        val entrepreneurshipWithFounder = EntrepreneurshipMapper.entrepreneurshipDtoToEntrepreneurshipWithFounder(entrepreneurshipDto)
        Result.success(entrepreneurshipWithFounder)
    } catch (e: Exception) {
        Result.failure(e)
    }

}