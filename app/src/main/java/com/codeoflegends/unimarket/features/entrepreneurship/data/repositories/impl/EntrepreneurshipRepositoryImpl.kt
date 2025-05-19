package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.mock.MockEntrepreneurshipDatabase
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipRepository
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class EntrepreneurshipRepositoryImpl @Inject constructor() : EntrepreneurshipRepository {

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

    override suspend fun getAllEntrepreneurships(): Result<List<Entrepreneurship>> = try {
        val entrepreneurships = MockEntrepreneurshipDatabase.getAllEntrepreneurships()
        Result.success(entrepreneurships)
    } catch (e: Exception) {
        Result.failure(e)
    }
}