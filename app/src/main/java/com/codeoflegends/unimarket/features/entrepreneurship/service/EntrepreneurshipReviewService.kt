package com.codeoflegends.unimarket.features.entrepreneurship.service

import com.codeoflegends.unimarket.core.dto.DirectusDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.ReviewRatingDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface EntrepreneurshipReviewService {
    @GET("/items/EntrepreneurshipReview")
    suspend fun getEntrepreneurshipReviews(
        @QueryMap query: Map<String, String>
    ): DirectusDto<List<EntrepreneurshipReviewDto>>

    @GET("/items/EntrepreneurshipReview")
    suspend fun getEntrepreneurshipRating(
        @QueryMap query: Map<String, String>
    ): DirectusDto<List<ReviewRatingDto>>

    @POST("/items/EntrepreneurshipReview")
    suspend fun createEntrepreneurshipReview(
        @Body entrepreneurshipReview: NewEntrepreneurshipReviewDto
    )

    @DELETE("/items/EntrepreneurshipReview/{reviewId}")
    suspend fun deleteEntrepreneurshipReview(
        @Path("reviewId") reviewId: String
    )
}