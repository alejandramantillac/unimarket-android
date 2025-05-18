package com.codeoflegends.unimarket.features.order.data.mock

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.model.OrderProduct
import com.codeoflegends.unimarket.features.order.data.model.Payment
import com.codeoflegends.unimarket.features.order.data.model.Client
import com.codeoflegends.unimarket.features.order.data.model.Delivery
import com.codeoflegends.unimarket.features.order.ui.components.OrderStatus
import com.codeoflegends.unimarket.features.product.data.mock.MockProductDatabase

object MockOrderDatabase {

    fun getMockOrder(): Order {

        val mockClient = Client(
            name = "Juan Pérez",
            sinceYear = 2018.toString(),
            email = "juan.perez@example.com",
            phone = "+57 300 123 4567",
            photo = "https://via.placeholder.com/64"
        )

        val mockProducts = MockProductDatabase.getAllProducts().map { product ->
            OrderProduct(
                product = product,
                quantity = (1..5).random()
            )
        }

        val mockStatusHistory = listOf(
            OrderStatus("Pedido recibido", "2023-10-01"),
            OrderStatus("En preparación", "2023-10-02"),
            OrderStatus("En camino", "2023-10-03")
        )

        val mockPayment = Payment(
            method = "Nequi #317-389-7769",
            products = mockProducts,
            evidenceImage = "https://via.placeholder.com/150"
        )

        val mockDelivery = Delivery(
            method = "Domicilio",
            location = "Calle 123 #45-67, Bogotá",
            dateTime = "2023-10-05 15:00"
        )

        return Order(
            client = mockClient,
            products = mockProducts,
            status = mockStatusHistory,
            payment = mockPayment,
            date = "2023-10-04",
            delivery = mockDelivery
        )
    }
}