package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.model.Product
import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(private val repository: IProductRepository) {
    suspend operator fun invoke(product: Product): Result<Unit> {
        return repository.createProduct(product)
    }
} 