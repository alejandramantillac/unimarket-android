package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.dto.SimpleProduct
import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(): List<SimpleProduct> {
        return repository.getAllProducts().getOrThrow()
    }
} 