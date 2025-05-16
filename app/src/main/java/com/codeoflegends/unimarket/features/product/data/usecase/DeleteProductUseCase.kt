package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.repositories.IProductRepository
import javax.inject.Inject
import java.util.UUID

class DeleteProductUseCase @Inject constructor(
    private val repository: IProductRepository
) {
    suspend operator fun invoke(id: UUID) {
        repository.deleteProduct(id).getOrThrow()
    }
} 