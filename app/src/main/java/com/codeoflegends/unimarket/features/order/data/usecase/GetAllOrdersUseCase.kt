package com.codeoflegends.unimarket.features.order.data.usecase

import android.util.Log
import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import java.util.UUID
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
) {
    suspend operator fun invoke(entrepreneurshipId: UUID): List<Order>{
        Log.d("GetAllOrdersUseCase", "Fetching all orders for entrepreneurshipId: $entrepreneurshipId")
        return repository.getAllOrders(entrepreneurshipId).getOrThrow()
    }
}
