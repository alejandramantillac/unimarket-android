package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import android.util.Log
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class ReviewRatingDto(
    val count: Count,
    val avg: Avg
) {
    companion object {
        fun query(entrepreneurshipId: UUID) : DirectusQuery {
            val query = DirectusQuery()
                .aggregate(
                    function = DirectusQuery.AggregateFunction.COUNT,
                    field = "id"
                )
                .aggregate(
                    function = DirectusQuery.AggregateFunction.AVG,
                    field = "rating"
                )
                .filter(
                    field = "entrepreneurship",
                    operator = "eq",
                    value = entrepreneurshipId.toString()
                )

            Log.i("ReviewRating", "Creating DirectusQuery for ReviewRating ${query.build()}")
            return query
        }
    }
}

data class Count(
    val id: String,
)

data class Avg(
    val rating: Float
)