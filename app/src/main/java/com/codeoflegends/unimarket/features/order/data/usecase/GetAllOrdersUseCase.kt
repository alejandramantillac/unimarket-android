package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import java.util.UUID
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
) {
    suspend operator fun invoke(entrepreneurshipId: UUID): List<Order>{
        return repository.getAllOrders(entrepreneurshipId).getOrThrow()
    }
}
