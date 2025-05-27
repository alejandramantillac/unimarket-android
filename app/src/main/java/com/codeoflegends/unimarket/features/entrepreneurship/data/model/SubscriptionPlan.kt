package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import java.util.UUID

data class SubscriptionPlan(
    val id: UUID,
    val name: String,
    val price: Double,
    val description: String,
    val benefits: String
)
