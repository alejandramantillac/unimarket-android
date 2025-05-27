package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipCategoryRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipCategoryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntrepreneurshipCategoryRepositoryImpl @Inject constructor(
    private val  entrepreneurshipCategoryService: EntrepreneurshipCategoryService,
    service: EntrepreneurshipCategoryService
): EntrepreneurshipCategoryRepository {
    override suspend fun getEntrepreneurshipCategories(): Result<List<EntrepreneurshipCategory>> {
        return try {
            val response = entrepreneurshipCategoryService.getEntrepreneurshipCategory()
                Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}