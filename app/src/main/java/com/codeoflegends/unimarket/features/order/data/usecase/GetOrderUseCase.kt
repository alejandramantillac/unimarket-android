package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import java.util.UUID
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(id: UUID): Order{
        return repository.getOrder(id).getOrThrow()
    }
}