package com.codeoflegends.unimarket.features.order.data.datasource

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderDto
import com.codeoflegends.unimarket.features.order.data.dto.get.OrderListDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdateDeliveryDto
import com.codeoflegends.unimarket.features.order.data.dto.update.UpdateOrderDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface OrderService {

    @GET("/items/Order")
    suspend fun getAllOrders(
        @QueryMap query: Map<String, String>
    ): DirectusDto<List<OrderListDto>>

    @GET("/items/Order/{orderId}")
    suspend fun getOrder(
        @Path("orderId") orderd: String,
        @QueryMap query: Map<String, String> = OrderDto.query().build()
    ): DirectusDto<OrderDto>

    @PATCH("/items/Order/{orderId}")
    suspend fun updateOrder(
        @Path("orderId") orderId: String,
        @Body order: UpdateOrderDto
    ): Response<DirectusDto<OrderDto>>
}
