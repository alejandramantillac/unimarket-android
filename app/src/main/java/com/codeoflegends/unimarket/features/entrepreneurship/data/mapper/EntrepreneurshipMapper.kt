package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import android.util.Log
import com.codeoflegends.unimarket.core.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipCustomizationDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.create.NewEntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update.UpdateEntrepreneurshipCustomizationDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.update.UpdateEntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object EntrepreneurshipMapper {
    fun entrepreneurshipDtoToEntrepreneurship(dto: EntrepreneurshipDto): Entrepreneurship {
        return Entrepreneurship(
            id = dto.id,
            name = dto.name,
            slogan = dto.slogan,
            description = dto.description,
            creationDate = LocalDateTime.parse(dto.creation_date, DateTimeFormatter.ISO_DATE_TIME),
            email = dto.email,
            phone = dto.phone,
            subscription = dto.subscription,
            status = dto.status,
            category = dto.category,
            userFounder = dto.user_founder,
            deletedAt = dto.deleted_at,
            partners = dto.partners,
            products = dto.products,
            collaborations = dto.collaborations,
            orders = dto.orders,
            socialNetworks = dto.social_networks.map { SocialNetwork(it, "") },
            tags = dto.tags.map { tagDto ->
                Log.d(
                    "EntrepreneurshipMapper",
                    "Processing tag: id=${tagDto.id}, name=${tagDto.name}"
                )
                Tag(
                    id = tagDto.id,
                    name = tagDto.name
                )
            },
            customization = EntrepreneurshipCustomization(
                id = dto.customization.id,
                profileImg = dto.customization.profile_img,
                bannerImg = dto.customization.banner_img,
                color1 = dto.customization.color1,
                color2 = dto.customization.color2
            )
        )
    }

    fun toNewEntrepreneurshipDto(entrepreneurship: Entrepreneurship): NewEntrepreneurshipDto {
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
            category = entrepreneurship.category,
            userFounder = entrepreneurship.userFounder,
            socialNetworks = entrepreneurship.socialNetworks,
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