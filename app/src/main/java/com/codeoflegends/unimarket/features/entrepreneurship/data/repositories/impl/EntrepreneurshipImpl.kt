package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipMapper
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipWithFounder
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.mock.MockEntrepreneurshipDatabase
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
/*
@Singleton
class EntrepreneurshipImpl @Inject constructor() :IEntrepreneurshipRepository {

    override suspend fun createEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> = try {
        MockEntrepreneurshipDatabase.addEntrepreneurship(entrepreneurship)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> = try {
        MockEntrepreneurshipDatabase.updateEntrepreneurship(entrepreneurship)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteEntrepreneurship(entrepreneurshipId: UUID): Result<Unit> = try {
        MockEntrepreneurshipDatabase.deleteEntrepreneurship(entrepreneurshipId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getEntrepreneurship(entrepreneurshipId: UUID): Result<Entrepreneurship> = try {
        val entrepreneurship = MockEntrepreneurshipDatabase.getEntrepreneurship(entrepreneurshipId)
        if (entrepreneurship != null) Result.success(entrepreneurship)
        else Result.failure(Exception("Emprendimiento no encontrado"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllEntrepreneurships(): Result<List<Entrepreneurship>> {
        val entrepreneurshipList = MockEntrepreneurshipDatabase.getAllEntrepreneurships()
        return if (entrepreneurshipList.isNotEmpty()) {
            Result.success(entrepreneurshipList)
        } else {
            Result.failure(Exception("No hay emprendimientos disponibles"))
        }
    }

    override suspend fun getEntrepreneurshipWithFounder(entrepreneurshipId: UUID): Result<EntrepreneurshipWithFounder> = try {
        val entrepreneurshipDto = entrepreneurshipService.getEntrepreneurship(
            entrepreneurshipId.toString(),
            EntrepreneurshipDto.query().build()
        ).data ?: return Result.failure(Exception("No se encontró el emprendimiento con id: $entrepreneurshipId"))

        val entrepreneurshipWithFounder = EntrepreneurshipMapper.entrepreneurshipDtoToEntrepreneurshipWithFounder(entrepreneurshipDto)
        Result.success(entrepreneurshipWithFounder)
    } catch (e: Exception) {
        Result.failure(Exception("Error al obtener el emprendimiento con fundador: ${e.message}", e))
    }

}

 */