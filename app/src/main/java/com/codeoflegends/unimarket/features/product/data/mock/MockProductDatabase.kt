package com.codeoflegends.unimarket.features.product.data.mock

import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import java.util.UUID

object MockProductDatabase {
    private val products = mutableMapOf(
        UUID.fromString("11111111-1111-1111-1111-111111111111") to Product(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            business = "Tienda de Ropa",
            category = "Ropa",
            name = "Camiseta Básica",
            description = "Camiseta 100% algodón",
            price = 19.99,
            lowStockAlert = 10,
            published = true,
            variants = listOf(
                ProductVariant(
                    id = UUID.fromString("aaaa1111-1111-1111-1111-111111111111"),
                    productId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
                    name = "Roja",
                    stock = 5,
                    variantImages = listOf()
                ),
                ProductVariant(
                    id = UUID.fromString("aaaa2222-2222-2222-2222-222222222222"),
                    productId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
                    name = "Azul",
                    stock = 3,
                    variantImages = listOf()
                )
            ),
            specifications = listOf(
                ProductSpecification(
                    id = UUID.fromString("cccc1111-1111-1111-1111-111111111111"),
                    key = "Batería",
                    value = "Hasta 20 horas",
                    product = UUID.fromString("11111111-1111-1111-1111-111111111111")
                ),
                ProductSpecification(
                    id = UUID.fromString("cccc2222-2222-2222-2222-222222222222"),
                    key = "Peso",
                    value = "250g",
                    product = UUID.fromString("11111111-1111-1111-1111-111111111111")
                )
            )
        ),
        UUID.fromString("22222222-2222-2222-2222-222222222222") to Product(
            id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            business = "Electrónica XYZ",
            category = "Electrónica",
            name = "Smartphone XYZ",
            description = "Último modelo con 128GB",
            price = 599.99,
            lowStockAlert = 5,
            published = true,
            variants = listOf(
                ProductVariant(
                    id = UUID.fromString("bbbb1111-1111-1111-1111-111111111111"),
                    productId = UUID.fromString("22222222-2222-2222-2222-222222222222"),
                    name = "128GB Negro",
                    stock = 2,
                    variantImages = listOf()
                )
            ),
            specifications = listOf(
                ProductSpecification(
                    id = UUID.fromString("dddd1111-1111-1111-1111-111111111111"),
                    key = "Pantalla",
                    value = "6.5 pulgadas",
                    product = UUID.fromString("22222222-2222-2222-2222-222222222222")
                )
            )
        ),
        UUID.fromString("33333333-3333-3333-3333-333333333333") to Product(
            id = UUID.fromString("33333333-3333-3333-3333-333333333333"),
            business = "Supermercado ABC",
            category = "Alimentos",
            name = "Arroz Premium",
            description = "Arroz de grano largo",
            price = 4.99,
            lowStockAlert = 20,
            published = true,
            variants = emptyList(),
            specifications = emptyList()
        )
    )

    fun getProduct(id: UUID): Product? = products[id]
    
    fun getAllProducts(): List<Product> = products.values.toList()
    
    fun addProduct(product: Product) {
        products[product.id ?: generateId()] = product
    }
    
    fun updateProduct(product: Product) {
        product.id?.let { products[it] = product }
    }
    
    fun deleteProduct(id: UUID) {
        products.remove(id)
    }
    
    private fun generateId(): UUID = UUID.randomUUID()
} 