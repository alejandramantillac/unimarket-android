package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.ReviewRating
import java.util.UUID

interface EntrepreneurshipReviewRepository {
    suspend fun getEntrepreneurshipReviews(entrepreneurshipId: UUID, page: Int = 1, limit: Int = 10): Result<List<EntrepreneurshipReview>>
    suspend fun createEntrepreneurshipReview(review: EntrepreneurshipReview): Result<Unit>
    suspend fun updateEntrepreneurshipReview(review: EntrepreneurshipReview): Result<Unit>
    suspend fun deleteEntrepreneurshipReview(reviewId: String): Result<Unit>
    suspend fun getEntrepreneurshipRating(entrepreneurshipId: UUID): Result<ReviewRating>
}