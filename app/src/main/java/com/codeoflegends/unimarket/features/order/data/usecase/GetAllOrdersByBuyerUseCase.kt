package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.getOrThrow

class GetAllOrdersByBuyerUseCase @Inject constructor(
    private val repository: OrderRepository,
) {
    suspend operator fun invoke(): List<Order> {
        val defaultBuyer = UUID.fromString("00000000-0000-0000-0000-000000000007")
        return repository.getAllOrdersByBuyer(defaultBuyer).getOrThrow()
    }
}
