package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipReviewDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipReview
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object EntrepreneurshipMapper {
    fun entrepreneurshipDtoToEntrepreneurship(dto: EntrepreneurshipDto): Entrepreneurship {
        return Entrepreneurship(
            id = dto.id,
            name = dto.name,
            slogan = dto.slogan,
            description = dto.description,
            creationDate = LocalDateTime.parse(dto.creationDate, DateTimeFormatter.ISO_DATE_TIME),
            email = dto.email,
            phone = dto.phone,
            //subscription = dto.subscription,
            status = dto.status,
            category = dto.category,
            userFounder = dto.userFounder,
            deletedAt = dto.deletedAt,
            //partners = dto.partners,
            //products = dto.products,
            //collaborations = dto.collaborations,
            //orders = dto.orders,
            //socialNetworks = dto.social_networks.map { SocialNetwork(1,it,"") },
            tags = dto.tags.map {
                Tag(
                    id = UUID.fromString(it.tagsId.id),
                    name = it.tagsId.name
                )
            },
             customization = EntrepreneurshipCustomization(
                id = UUID.fromString(dto.customization.id),
                profileImg = dto.customization.profile_img,
                bannerImg = dto.customization.banner_img,
                color1 = dto.customization.color1,
                color2 = dto.customization.color2
            ),
            reviews = dto.reviews?.map {
                EntrepreneurshipReview(
                    id = UUID.fromString(it.id),
                    userCreated = User(
                        id = UUID.fromString(it.userCreated.id),
                        firstName = it.userCreated.firstName,
                        lastName = it.userCreated.lastName,
                        email = it.userCreated.email,
                        profile = UserProfile(
                            profilePicture = it.userCreated.profile.profilePicture,
                            userRating = it.userCreated.profile.userRating,
                            partnerRating = it.userCreated.profile.partnerRating,
                            registrationDate = LocalDateTime.parse(it.userCreated.profile.registrationDate, DateTimeFormatter.ISO_DATE_TIME)
                        )
                    ),
                    dateCreated = LocalDateTime.parse(it.dateCreated, DateTimeFormatter.ISO_DATE_TIME),
                    rating = it.rating,
                    comment = it.comment
                )
            } ?: emptyList()
        )
    }
}