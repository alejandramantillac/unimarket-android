package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import javax.inject.Inject
import java.util.UUID

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: UUID): Product {
        return repository.getProduct(id).getOrThrow()
    }
} 