package com.codeoflegends.unimarket.features.product.data.mock

import com.codeoflegends.unimarket.features.product.data.model.Product
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
            published = true
        ),
        UUID.fromString("22222222-2222-2222-2222-222222222222") to Product(
            id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            business = "Electrónica XYZ",
            category = "Electrónica",
            name = "Smartphone XYZ",
            description = "Último modelo con 128GB",
            price = 599.99,
            lowStockAlert = 5,
            published = true
        ),
        UUID.fromString("33333333-3333-3333-3333-333333333333") to Product(
            id = UUID.fromString("33333333-3333-3333-3333-333333333333"),
            business = "Supermercado ABC",
            category = "Alimentos",
            name = "Arroz Premium",
            description = "Arroz de grano largo",
            price = 4.99,
            lowStockAlert = 20,
            published = true
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