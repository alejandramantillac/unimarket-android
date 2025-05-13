package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val repository: IProductRepository) {
    suspend operator fun invoke(productId: String): Result<Unit> {
        return repository.deleteProduct(productId)
    }
} 