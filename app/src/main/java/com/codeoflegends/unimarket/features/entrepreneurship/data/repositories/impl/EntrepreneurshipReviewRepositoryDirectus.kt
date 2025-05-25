package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.ReviewRatingDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipReviewMapper
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.ReviewRating
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.EntrepreneurshipReviewRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipReviewService
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntrepreneurshipReviewRepositoryDirectus @Inject constructor(
    private val entrepreneurshipReviewService: EntrepreneurshipReviewService
): EntrepreneurshipReviewRepository {
    override suspend fun getEntrepreneurshipReviews(entrepreneurshipId: UUID, page: Int, limit: Int): Result<List<EntrepreneurshipReview>> = try {
        val reviewsDto = entrepreneurshipReviewService.getEntrepreneurshipReviews(
            EntrepreneurshipReviewDto.query(page = page, limit = limit).build()
        ).data

        val entrepreneurshipReviews = reviewsDto.map { reviewDto ->
            EntrepreneurshipReviewMapper.entrepreneurshipReviewDtoToEntrepreneurshipReview(reviewDto)
        }

        Result.success(entrepreneurshipReviews)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun createEntrepreneurshipReview(review: EntrepreneurshipReview): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEntrepreneurshipReview(review: EntrepreneurshipReview): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntrepreneurshipReview(reviewId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getEntrepreneurshipRating(entrepreneurshipId: UUID): Result<ReviewRating> = try {
        val ratingDto = entrepreneurshipReviewService.getEntrepreneurshipRating(
            ReviewRatingDto.query(entrepreneurshipId).build()
        ).data

        Result.success(EntrepreneurshipReviewMapper.entrepreneurshipReviewRatingDtoToEntrepreneurshipReviewRating(ratingDto[0]))
    } catch (e: Exception) {
        Result.failure(e)
    }
}