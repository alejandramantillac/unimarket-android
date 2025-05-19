package com.codeoflegends.unimarket.features.product.data.usecase

import com.codeoflegends.unimarket.features.product.data.model.ProductCategory
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductCategoryRepository
import javax.inject.Inject

class GetAllProductCategoriesUseCase @Inject constructor(
    private val repository: ProductCategoryRepository
) {
    suspend operator fun invoke(): List<ProductCategory> {
        return repository.getAllCategories().getOrThrow()
    }
} 