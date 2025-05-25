package com.codeoflegends.unimarket.features.order.data.mock

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.Client
import com.codeoflegends.unimarket.features.order.ui.components.OrderStatus
import com.codeoflegends.unimarket.features.product.data.mock.MockProductDatabase
import java.util.UUID

object MockOrderDatabase {

    private val orders = mutableMapOf(
        UUID.fromString("44444444-4444-4444-4444-444444444444") to Order(
            id = UUID.fromString("44444444-4444-4444-4444-444444444444"),
            client = Client(
                name = "Juan Pérez",
                sinceYear = "2018",
                email = "juan.perez@example.com",
                phone = "+57 300 123 4567",
                photo = "https://via.placeholder.com/64"
            ),
            products = listOf(
                OrderProduct(
                    product = MockProductDatabase.getProduct(UUID.fromString("11111111-1111-1111-1111-111111111111"))!!,
                    quantity = 2
                ),
                OrderProduct(
                    product = MockProductDatabase.getProduct(UUID.fromString("22222222-2222-2222-2222-222222222222"))!!,
                    quantity = 1
                )
            ),
            status = listOf(
                OrderStatus("Pedido recibido", "2023-10-01"),
                OrderStatus("En preparación", "2023-10-02"),
                OrderStatus("En camino", "2023-10-03")
            ),
            payment = Payment(
                method = "Nequi #317-389-7769",
                products = listOf(
                    OrderProduct(
                        product = MockProductDatabase.getProduct(UUID.fromString("11111111-1111-1111-1111-111111111111"))!!,
                        quantity = 2
                    ),
                    OrderProduct(
                        product = MockProductDatabase.getProduct(UUID.fromString("22222222-2222-2222-2222-222222222222"))!!,
                        quantity = 1
                    )
                ),
                evidenceImage = "https://via.placeholder.com/150"
            ),
            date = "2023-10-04",
            delivery = Delivery(
                method = "Domicilio",
                location = "Calle 123 #45-67, Bogotá",
                dateTime = "2023-10-05 15:00"
            )
        ),
        UUID.fromString("55555555-5555-5555-5555-555555555555") to Order(
            id = UUID.fromString("55555555-5555-5555-5555-555555555555"),
            client = Client(
                name = "María López",
                sinceYear = "2020",
                email = "maria.lopez@example.com",
                phone = "+57 310 987 6543",
                photo = "https://via.placeholder.com/64"
            ),
            products = listOf(
                OrderProduct(
                    product = MockProductDatabase.getProduct(UUID.fromString("33333333-3333-3333-3333-333333333333"))!!,
                    quantity = 3
                )
            ),
            status = listOf(
                OrderStatus("Pedido recibido", "2023-10-02"),
                OrderStatus("En preparación", "2023-10-03")
            ),
            payment = Payment(
                method = "Tarjeta de crédito",
                products = listOf(
                    OrderProduct(
                        product = MockProductDatabase.getProduct(UUID.fromString("33333333-3333-3333-3333-333333333333"))!!,
                        quantity = 3
                    )
                ),
                evidenceImage = "https://via.placeholder.com/150"
            ),
            date = "2023-10-03",
            delivery = Delivery(
                method = "Recogida en tienda",
                location = "Tienda Principal, Bogotá",
                dateTime = "2023-10-04 10:00"
            )
        )
    )

    fun getOrder(id: UUID): Order? = orders[id]

    fun getAllOrders(): List<Order> = orders.values.toList()
}