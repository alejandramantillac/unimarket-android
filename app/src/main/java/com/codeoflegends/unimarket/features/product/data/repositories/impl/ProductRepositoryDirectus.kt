package com.codeoflegends.unimarket.features.product.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.core.dto.DeleteDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.product.data.datasource.ProductService
import com.codeoflegends.unimarket.features.product.data.dto.create.NewProductDto
import com.codeoflegends.unimarket.features.product.data.dto.create.NewReviewDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductDetailDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductReviewDto
import com.codeoflegends.unimarket.features.product.data.mapper.ProductMapper
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import com.codeoflegends.unimarket.features.product.data.model.Review

@Singleton
class ProductRepositoryDirectus @Inject constructor(
    private val productService: ProductService,
    private val authRepository: AuthRepository,
    private val userGetDataRepository: IUserGetDataRepository
) : ProductRepository {

    override suspend fun createProduct(product: Product): Result<Unit> = try {
        val productDto = ProductMapper.toNewProductDto(product)
        productService.createProduct(productDto)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error creating product: ${e.message}")
        Result.failure(e)
    }

    override suspend fun updateProduct(product: Product): Result<Unit> = try {
        val productDto = ProductMapper.toUpdateProductDto(product)
        Log.d("ProductRepositoryDirectus", "Updating product: $productDto")
        productService.updateProduct(product.id.toString(), productDto)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error updating product: ${e.message}")
        Result.failure(e)
    }

    override suspend fun deleteProduct(productId: UUID): Result<Unit> = try {
        val isoTimestamp = Instant.now().atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT)

        productService.deleteProduct(productId.toString(), DeleteDto(
            deletedAt = isoTimestamp
        ))
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getProduct(productId: UUID): Result<Product> = try {
        val productDto = productService.getProduct(productId.toString(), ProductDetailDto.query().build()).data
        val product = ProductMapper.detailDtoToProduct(productDto)
        Result.success(product)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllProducts(): Result<List<Product>> = try {
        val productsDto = productService.getAllProducts(ProductListDto.query().build()).data
        val products = ProductMapper.listDtoToProductList(productsDto)
        Result.success(products)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching products: ${e.message}")
        Result.failure(e)
    }

    override suspend fun getAllProductsByQuery(
        entrepreneurshipId: UUID,
        nameContains: String,
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): Result<List<Product>> = try {
        val productsDto = productService.getAllProducts(
            ProductListDto.queryByEntrepreneurship(
                entrepreneurshipId = entrepreneurshipId.toString(),
                nameContains = nameContains,
                filters = filters,
                limit = limit,
                page = page
            ).build()
        ).data
        val products = ProductMapper.listDtoToProductList(productsDto)
        Result.success(products)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching products by entrepreneurship: ${e.message}")
        Result.failure(e)
    }

    override suspend fun createReview(productId: UUID, rating: Int, comment: String): Result<Unit> = try {
        val userDto = userGetDataRepository.getUserData().getOrThrow()
        val userProfileId = userDto.profile?.id
            ?: throw Exception("El usuario autenticado no tiene perfil asociado. No se puede calificar el producto.")
        val reviewDto = NewReviewDto(
            product = productId.toString(),
            rating = rating,
            comment = comment,
            user_profile = userProfileId
        )
        productService.createReview(reviewDto)
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error creating review: ${e.message}")
        Result.failure(e)
    }

    override suspend fun getProductReviews(productId: UUID, page: Int, limit: Int): Result<List<Review>> = try {
        Log.d("ProductRepositoryDirectus", "Fetching reviews for product: $productId")
        val query = ProductReviewDto.Companion.query(productId, page, limit).build()
        Log.d("ProductRepositoryDirectus", "Query: $query")
        val reviewsDto = productService.getProductReviews(query)
        Log.d("ProductRepositoryDirectus", "Received reviews DTO: ${'$'}{reviewsDto.data}")
        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val reviews = reviewsDto.data.mapNotNull { dto ->
            try {
                Log.d("ProductRepositoryDirectus", "Mapping review DTO: ${'$'}dto")
                Review(
                    id = UUID.fromString(dto.id),
                    productId = UUID.fromString(dto.product),
                    userProfileId = UUID.fromString(dto.user_profile),
                    rating = dto.rating,
                    comment = dto.comment,
                    creationDate = java.time.LocalDateTime.parse(dto.creation_date, formatter)
                )
            } catch (e: Exception) {
                Log.e("ProductRepositoryDirectus", "Error mapping review DTO: ${'$'}dto, error: ${'$'}{e.message}")
                null
            }
        }
        Log.d("ProductRepositoryDirectus", "Mapped reviews: ${'$'}reviews")
        Result.success(reviews)
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching product reviews: ${'$'}{e.message}", e)
        Result.failure(e)
    }
}
