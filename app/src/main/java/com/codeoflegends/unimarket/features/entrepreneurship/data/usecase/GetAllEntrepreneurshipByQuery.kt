package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipListDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.Product
import javax.inject.Inject

class GetAllEntrepreneurshipByQuery @Inject constructor(
    private val repository: IEntrepreneurshipRepository
)
{
    suspend operator fun invoke(
        nameContains: String = "",
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): List<EntrepreneurshipListDto> {
        return repository.getAllEntrepreneurshipsByQuery(
            nameContains = nameContains,
            filters = filters,
            limit = limit,
            page = page
        ).getOrThrow()
    }
}