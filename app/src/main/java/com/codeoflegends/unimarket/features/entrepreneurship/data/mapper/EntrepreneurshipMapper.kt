package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipCustomizationDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update.UpdateEntrepreneurshipCustomizationDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update.UpdateEntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCreate
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
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
            category = dto.category,
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
            )
        )
    }

    fun toNewEntrepreneurshipDto(entrepreneurship: EntrepreneurshipCreate, userId: UUID): NewEntrepreneurshipDto {
        return NewEntrepreneurshipDto(
            name = entrepreneurship.name,
            slogan = entrepreneurship.slogan,
            description = entrepreneurship.description,
            customization = NewEntrepreneurshipCustomizationDto(
                profileImg = entrepreneurship.customization.profileImg,
                bannerImg = entrepreneurship.customization.bannerImg,
                color1 = entrepreneurship.customization.color1,
                color2 = entrepreneurship.customization.color2
            ),
            email = entrepreneurship.email,
            phone = entrepreneurship.phone,
            category = entrepreneurship.category
        )
    }

    fun toUpdateEntrepreneurshipDto(entrepreneurship: Entrepreneurship): UpdateEntrepreneurshipDto {
        return UpdateEntrepreneurshipDto(
            name = entrepreneurship.name,
            slogan = entrepreneurship.slogan,
            description = entrepreneurship.description,
            customization = UpdateEntrepreneurshipCustomizationDto(
                id = entrepreneurship.customization.id!!,
                profileImg = entrepreneurship.customization.profileImg,
                bannerImg = entrepreneurship.customization.bannerImg,
                color1 = entrepreneurship.customization.color1,
                color2 = entrepreneurship.customization.color2
            ),
            email = entrepreneurship.email,
            phone = entrepreneurship.phone,
            category = entrepreneurship.category,
            socialNetworks = entrepreneurship.socialNetworks,
        )

    }
}