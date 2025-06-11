package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(order: Order): Order {
        repository.updateOrder(order).getOrThrow()
        return order
    }
}

