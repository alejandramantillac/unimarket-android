package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.product.data.repositories.interfaces.ProductRepository
import java.util.UUID
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
) {
    suspend operator fun invoke(): List<Order>{
        val defaultEntrepreneurship = UUID.fromString("00000000-0000-0000-0000-000000000007")
        return repository.getAllOrders(defaultEntrepreneurship).getOrThrow()
    }
}
