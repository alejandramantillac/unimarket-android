package com.codeoflegends.unimarket.features.order.data.usecase

import com.codeoflegends.unimarket.features.order.data.model.Order
import com.codeoflegends.unimarket.features.order.data.repositories.interfaces.OrderRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.getOrThrow

class GetAllOrdersByBuyerUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val userGetDataRepository: IUserGetDataRepository
) {
    suspend operator fun invoke(): List<Order> {
        val userDto = userGetDataRepository.getUserData().getOrThrow()
        val userId = userDto.id ?: throw Exception("User ID not found")
        return repository.getAllOrdersByBuyer(UUID.fromString(userId)).getOrThrow()
    }
}
