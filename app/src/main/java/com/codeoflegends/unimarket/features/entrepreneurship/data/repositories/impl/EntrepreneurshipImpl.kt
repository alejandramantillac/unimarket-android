package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.mock.MockEntrepreneurshipDatabase
import javax.inject.Inject
import javax.inject.Singleton

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

    override suspend fun deleteEntrepreneurship(entrepreneurshipId: String): Result<Unit> = try {
        MockEntrepreneurshipDatabase.deleteEntrepreneurship(entrepreneurshipId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getEntrepreneurship(entrepreneurshipId: String): Result<Entrepreneurship> = try {
        val entrepreneurship = MockEntrepreneurshipDatabase.getEntrepreneurship(entrepreneurshipId)
        if (entrepreneurship != null) Result.success(entrepreneurship)
        else Result.failure(Exception("Emprendimiento no encontrado"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllEntrepreneurship(): Result<List<Entrepreneurship>> {
        val entrepreneurshipList = MockEntrepreneurshipDatabase.getAllEntrepreneurship()
        return if (entrepreneurshipList.isNotEmpty()) {
            Result.success(entrepreneurshipList)
        } else {
            Result.failure(Exception("No hay emprendimientos disponibles"))
        }
    }
}