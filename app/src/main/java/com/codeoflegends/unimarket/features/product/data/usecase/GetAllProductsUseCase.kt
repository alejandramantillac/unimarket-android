package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import com.codeoflegends.unimarket.features.product.data.repositories.impl.ProductRepositoryImpl
import com.codeoflegends.unimarket.features.product.model.Product
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        val repoImpl = repository as ProductRepositoryImpl
        return repoImpl.getAllProducts().getOrThrow()
    }
} 