package com.codeoflegends.unimarket.features.product.data.mock

import com.codeoflegends.unimarket.features.product.data.model.Category
import com.codeoflegends.unimarket.features.product.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.product.data.model.Product
import com.codeoflegends.unimarket.features.product.data.model.ProductVariant
import com.codeoflegends.unimarket.features.product.data.model.ProductSpecification
import com.codeoflegends.unimarket.features.product.data.model.Review
import com.codeoflegends.unimarket.features.product.data.model.VariantImage
import java.util.UUID
import java.time.LocalDateTime

object MockProductDatabase {
    private val products = mutableMapOf(
        UUID.fromString("11111111-1111-1111-1111-111111111111") to Product(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            entrepreneurship = Entrepreneurship(
                id = UUID.fromString("00000000-0000-0000-0000-000000000007"),
                name = "Tienda de Alimentos",
            ),
            category = Category(
                name = "Moda",
                description = "Viste con estilo."
            ),
            name = "Camiseta Básica",
            description = "Camiseta 100% algodón",
            price = 19.99,
            stockAlert = 10,
            published = true,
            variants = listOf(
                ProductVariant(
                    id = UUID.fromString("aaaa1111-1111-1111-1111-111111111111"),
                    name = "Roja",
                    stock = 5,
                    variantImages = listOf(
                        VariantImage(
                            UUID.randomUUID(),
                            "https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=400&q=80"
                        )
                    )
                ),
                ProductVariant(
                    id = UUID.fromString("aaaa2222-2222-2222-2222-222222222222"),
                    name = "Azul",
                    stock = 3,
                    variantImages = listOf(
                        VariantImage(
                            UUID.randomUUID(),
                            "https://images.unsplash.com/photo-1519125323398-675f0ddb6308?w=400&q=80"
                        )
                    )
                )
            ),
            specifications = listOf(
                ProductSpecification(
                    id = UUID.fromString("cccc1111-1111-1111-1111-111111111111"),
                    key = "Batería",
                    value = "Hasta 20 horas",
                ),
                ProductSpecification(
                    id = UUID.fromString("cccc2222-2222-2222-2222-222222222222"),
                    key = "Peso",
                    value = "250g",
                )
            ),
            reviews = listOf(
                Review(
                    id = UUID.fromString("aaaa3333-3333-3333-3333-333333333333"),
                    productId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
                    userProfileId = UUID.fromString("bbbb3333-3333-3333-3333-333333333333"),
                    rating = 4,
                    comment = "Muy buena calidad",
                    creationDate = LocalDateTime.now()
                )
            )
        ),
        UUID.fromString("22222222-2222-2222-2222-222222222222") to Product(
            id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            entrepreneurship = Entrepreneurship(
                id = UUID.fromString("00000000-0000-0000-0000-000000000007"),
                name = "Tienda de Alimentos",
            ),
            category = Category(
                name = "Electrónica",
                description = "Últimos gadgets y tecnología."
            ),
            name = "Smartphone XYZ",
            description = "Último modelo con 128GB",
            price = 599.99,
            stockAlert = 5,
            published = true,
            variants = listOf(
                ProductVariant(
                    id = UUID.fromString("bbbb1111-1111-1111-1111-111111111111"),
                    name = "128GB Negro",
                    stock = 2,
                    variantImages = listOf(
                        VariantImage(
                            UUID.randomUUID(),
                            "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&q=80"
                        )
                    )
                )
            ),
            specifications = listOf(
                ProductSpecification(
                    id = UUID.fromString("dddd1111-1111-1111-1111-111111111111"),
                    key = "Pantalla",
                    value = "6.5 pulgadas",
                )
            ),
            reviews = listOf(
                Review(
                    id = UUID.fromString("bbbb3333-3333-3333-3333-333333333333"),
                    productId = UUID.fromString("22222222-2222-2222-2222-222222222222"),
                    userProfileId = UUID.fromString("cccc3333-3333-3333-3333-333333333333"),
                    rating = 5,
                    comment = "Excelente smartphone",
                    creationDate = LocalDateTime.now()
                )
            )
        ),
        UUID.fromString("33333333-3333-3333-3333-333333333333") to Product(
            id = UUID.fromString("33333333-3333-3333-3333-333333333333"),
            entrepreneurship = Entrepreneurship(
                id = UUID.fromString("00000000-0000-0000-0000-000000000007"),
                name = "Tienda de Alimentos",
            ),
            category = Category(
                name = "Alimentos",
                description = "Comida y bebidas."
            ),
            name = "Arroz Premium",
            description = "Arroz de grano largo",
            price = 4.99,
            stockAlert = 20,
            published = true,
            variants = emptyList(),
            specifications = emptyList(),
            reviews = emptyList()
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