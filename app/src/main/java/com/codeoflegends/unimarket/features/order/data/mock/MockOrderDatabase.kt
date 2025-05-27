package com.codeoflegends.unimarket.features.order.data.mock

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.OrderDetail
import java.util.UUID

object MockOrderDatabase {

    /*private val orders = mutableMapOf(
        UUID.fromString("44444444-4444-4444-4444-444444444444") to Order(
            id = UUID.fromString("44444444-4444-4444-4444-444444444444"),
            userCreated = User(
                firstName = "Juan",
                lastName = "Pérez",
                profile = User.Profile(
                    profilePicture = "https://via.placeholder.com/64"
                )
            ),
            orderDetails = listOf(
                OrderDetail(
                    product = MockProductDatabase.getProduct(UUID.fromString("11111111-1111-1111-1111-111111111111"))!!,
                    amount = 2
                ),
                OrderDetail(
                    product = MockProductDatabase.getProduct(UUID.fromString("22222222-2222-2222-2222-222222222222"))!!,
                    amount = 1
                )
            ),
            total = 50000,
            date = "2023-10-04",
            status = Order.status
        ),
        UUID.fromString("55555555-5555-5555-5555-555555555555") to Order(
            id = UUID.fromString("55555555-5555-5555-5555-555555555555"),
            userCreated = UserCreated(
                firstName = "María",
                lastName = "López",
                profile = UserCreated.Profile(
                    profilePicture = "https://via.placeholder.com/64"
                )
            ),
            orderDetails = listOf(
                OrderDetail(
                    product = MockProductDatabase.getProduct(UUID.fromString("33333333-3333-3333-3333-333333333333"))!!,
                    amount = 3
                )
            ),
            total = 75000,
            date = "2023-10-03",
            status = Order.Status.COMPLETED
        )
    )*/

  /*  fun getOrder(id: UUID): Order? = orders[id]

    fun getAllOrders(): List<Order> = orders.values.toList()*/
}