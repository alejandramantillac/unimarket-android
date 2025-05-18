package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.product.data.dto.ProductDetailDto
import com.codeoflegends.unimarket.features.product.data.dto.ProductListDto
import com.codeoflegends.unimarket.features.product.data.model.Category
import com.codeoflegends.unimarket.features.product.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

/**
 * Mapper class responsible for converting between data layer DTOs and domain models
 */
object ProductMapper {
    
    fun productToListDto(product: Product): ProductListDto {
        return ProductListDto(
            id = product.id!!,
            category = product.category.name,
            name = product.name,
            description = product.description,
            price = product.price,
            stockAlert = product.stockAlert,
            published = product.published,
            entrepreneurship = EntrepreneurshipDto(
                id = product.entrepreneurship.id.toString(),
                name = product.entrepreneurship.name
            )
        )
    }
    
    fun productToDetailDto(product: Product): ProductDetailDto {
        return ProductDetailDto(
            id = product.id!!,
            category = product.category.name,
            name = product.name,
            description = product.description,
            price = product.price,
            stockAlert = product.stockAlert,
            published = product.published,
            entrepreneurship = EntrepreneurshipDto(
                id = product.entrepreneurship.id.toString(),
                name = product.entrepreneurship.name
            ),
            variants = product.variants.map { VariantMapper.mapToDto(it) },
            specifications = product.specifications.map { SpecificationMapper.mapToDto(it) }
        )
    }
    
    fun listDtoToProduct(dto: ProductListDto): Product {
        return Product(
            id = dto.id,
            category = Category(
                name = dto.category,
                description = "" // This field is not available in the DTO
            ),
            name = dto.name,
            description = dto.description,
            price = dto.price,
            stockAlert = dto.stockAlert,
            published = dto.published,
            entrepreneurship = Entrepreneurship(
                id = UUID.fromString(dto.entrepreneurship.id),
                name = dto.entrepreneurship.name
            )
        )
    }
    
    fun detailDtoToProduct(dto: ProductDetailDto): Product {
        return Product(
            id = dto.id,
            category = Category(
                name = dto.category,
                description = "" // This field is not available in the DTO
            ),
            name = dto.name,
            description = dto.description,
            price = dto.price,
            stockAlert = dto.stockAlert,
            published = dto.published,
            entrepreneurship = Entrepreneurship(
                id = UUID.fromString(dto.entrepreneurship.id),
                name = dto.entrepreneurship.name
            ),
            variants = dto.variants.map { VariantMapper.mapFromDto(it) },
            specifications = dto.specifications.map { SpecificationMapper.mapFromDto(it) },
        )
    }
    
    fun listDtoToProductList(dtos: List<ProductListDto>): List<Product> {
        return dtos.map { listDtoToProduct(it) }
    }
} 