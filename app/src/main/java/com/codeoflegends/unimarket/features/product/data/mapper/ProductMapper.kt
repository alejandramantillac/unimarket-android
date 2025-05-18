package com.codeoflegends.unimarket.features.product.data.mapper

import com.codeoflegends.unimarket.features.product.data.dto.create.EntrepreneurshipCreateDto
import com.codeoflegends.unimarket.features.product.data.dto.create.NewProductDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductDetailDto
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.dto.update.UpdateProductDto
import com.codeoflegends.unimarket.features.product.data.model.ProductCategory
import com.codeoflegends.unimarket.features.product.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.util.UUID

/**
 * Mapper class responsible for converting between data layer DTOs and domain models
 */
object ProductMapper {
    
    fun listDtoToProduct(dto: ProductListDto): Product {
        return Product(
            id = dto.id,
            category = ProductCategory(
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
            category = ProductCategory(
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

    fun toNewProductDto(product: Product): NewProductDto {
        return NewProductDto(
            category = product.category.name,
            name = product.name,
            description = product.description,
            price = product.price,
            stockAlert = product.stockAlert,
            published = product.published,
            entrepreneurship = EntrepreneurshipCreateDto(
                id = product.entrepreneurship.id.toString(),
            ),
            variants = product.variants.map { VariantMapper.toNewVariantDto(it) },
            specifications = product.specifications.map { SpecificationMapper.toNewSpecificationDto(it) }
        )
    }
    
    fun listDtoToProductList(dtos: List<ProductListDto>): List<Product> {
        return dtos.map { listDtoToProduct(it) }
    }

    fun toUpdateProductDto(product: Product): UpdateProductDto {
        return UpdateProductDto(
            category = product.category.name,
            name = product.name,
            description = product.description,
            price = product.price,
            stockAlert = product.stockAlert,
            published = product.published,
            variants = product.variants.map { VariantMapper.toUpdateVariantDto(it) },
            specifications = product.specifications.map { SpecificationMapper.toUpdateSpecificationDto(it) }
        )
    }
} 