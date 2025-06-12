package com.codeoflegends.unimarket.features.order.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.features.order.data.datasource.OrderStatusService
import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderStatusRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderStatusRepositoryDirectus @Inject constructor(
    private val orderStatusService: OrderStatusService
) : OrderStatusRepository {
    override suspend fun getAllOrderStatuses(): Result<List<OrderStatus>> {
        return try {
            val response = orderStatusService.getAllOrderStatuses()
            Result.success(response.data.map { dto ->
                OrderStatus(
                    id = dto.id,
                    name = dto.name
                )
            })
        } catch (e: Exception) {
            Log.e("OrderStatusRepository", "Error getting order statuses", e)
            Result.failure(e)
        }
    }
} 