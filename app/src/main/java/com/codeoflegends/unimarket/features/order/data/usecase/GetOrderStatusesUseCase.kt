package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderStatusRepository
import javax.inject.Inject

class GetOrderStatusesUseCase @Inject constructor(
    private val repository: OrderStatusRepository
) {
    suspend operator fun invoke(): Result<List<OrderStatus>> {
        return repository.getAllOrderStatuses()
    }
} 