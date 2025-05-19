package com.codeoflegends.unimarket.features.entrepreneurship.data.model

import android.telephony.SubscriptionPlan
import java.time.LocalDateTime
import java.util.UUID

data class EntrepreneurshipSubscription(
    val id: UUID,
    val subscriptionPlan: SubscriptionPlan,
    val cutoffDate: LocalDateTime,
    val lastPayment: LocalDateTime
)
