package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import com.codeoflegends.unimarket.features.product.data.model.Product
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product): Product {
        repository.updateProduct(product).getOrThrow()
        return product
    }
} 