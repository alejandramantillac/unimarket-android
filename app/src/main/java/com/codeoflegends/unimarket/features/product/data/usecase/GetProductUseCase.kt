package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.model.Product
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(id: String): Product {
        return repository.getProduct(id).getOrThrow()
    }
} 