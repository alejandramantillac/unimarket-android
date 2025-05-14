package com.codeoflegends.unimarket.features.product.data.mock

import com.codeoflegends.unimarket.features.product.model.Product

object MockProductDatabase {
    private val products = mutableMapOf(
        "1" to Product(
            id = "1",
            business = "Tienda de Ropa",
            category = "Ropa",
            name = "Camiseta Básica",
            description = "Camiseta 100% algodón",
            sku = "CAM-001",
            price = 19.99,
            quantity = 100,
            lowStockAlert = 10,
            published = true
        ),
        "2" to Product(
            id = "2",
            business = "Electrónica XYZ",
            category = "Electrónica",
            name = "Smartphone XYZ",
            description = "Último modelo con 128GB",
            sku = "PHN-001",
            price = 599.99,
            quantity = 50,
            lowStockAlert = 5,
            published = true
        ),
        "3" to Product(
            id = "3",
            business = "Supermercado ABC",
            category = "Alimentos",
            name = "Arroz Premium",
            description = "Arroz de grano largo",
            sku = "ARR-001",
            price = 4.99,
            quantity = 200,
            lowStockAlert = 20,
            published = true
        )
    )

    fun getProduct(id: String): Product? = products[id]
    
    fun getAllProducts(): List<Product> = products.values.toList()
    
    fun addProduct(product: Product) {
        products[product.id ?: generateId()] = product
    }
    
    fun updateProduct(product: Product) {
        product.id?.let { products[it] = product }
    }
    
    fun deleteProduct(id: String) {
        products.remove(id)
    }
    
    private fun generateId(): String = (products.size + 1).toString()
} 