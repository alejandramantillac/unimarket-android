package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.ReviewRatingDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.ReviewRating
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object EntrepreneurshipReviewMapper {
    fun entrepreneurshipReviewDtoToEntrepreneurshipReview(
        dto: EntrepreneurshipReviewDto
    ): EntrepreneurshipReview {
        return EntrepreneurshipReview(
            id = dto.id,
            userCreated = User(
                id = UUID.fromString(dto.userCreated.id),
                firstName = dto.userCreated.firstName,
                lastName = dto.userCreated.lastName,
                email = dto.userCreated.email,
                profile = UserProfile(
                    profilePicture = dto.userCreated.profile.profilePicture,
                    userRating = dto.userCreated.profile.userRating,
                    partnerRating = dto.userCreated.profile.partnerRating,
                    registrationDate = LocalDateTime.parse(dto.userCreated.profile.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                )
            ),
            dateCreated = LocalDateTime.parse(dto.dateCreated, DateTimeFormatter.ISO_DATE_TIME),
            rating = dto.rating,
            comment = dto.comment
        )
    }

    fun entrepreneurshipReviewRatingDtoToEntrepreneurshipReviewRating(
        dto: ReviewRatingDto
    ): ReviewRating {
        return ReviewRating(
            totalReviews = dto.count.id.toInt(),
            avgReview = dto.avg.rating
        )
    }
}