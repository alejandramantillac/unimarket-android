package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipReviewDto
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
                profile = dto.userCreated.profile?.let { profileDto ->
                    UserProfile(
                        profilePicture = profileDto.profilePicture,
                        userRating = profileDto.userRating,
                        partnerRating = profileDto.partnerRating,
                        registrationDate = LocalDateTime.parse(profileDto.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                    )
                } ?: UserProfile("", 0f, 0f, LocalDateTime.now())
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

    fun newEntrepreneurshipReviewDto(
        entrepreneurship: UUID,
        rating: Int,
        comment: String
    ): NewEntrepreneurshipReviewDto {
        return NewEntrepreneurshipReviewDto(
            entrepreneurship = entrepreneurship.toString(),
            rating = rating,
            comment = comment
        )
    }
}