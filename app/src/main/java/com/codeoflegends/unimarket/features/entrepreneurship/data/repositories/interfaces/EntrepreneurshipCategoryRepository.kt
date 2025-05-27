package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces


import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory

interface EntrepreneurshipCategoryRepository {
    suspend fun getEntrepreneurshipCategories(): Result<List<EntrepreneurshipCategory>>

}