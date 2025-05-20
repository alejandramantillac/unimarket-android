package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import android.util.Log
import com.codeoflegends.unimarket.core.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
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
            creationDate = LocalDateTime.parse(dto.creation_date, DateTimeFormatter.ISO_DATE_TIME),
            email = dto.email,
            phone = dto.phone,
            //subscription = dto.subscription,
            status = dto.status,
            category = dto.category,
            userFounder = dto.user_founder,
            deletedAt = dto.deleted_at,
            //partners = dto.partners,
            //products = dto.products,
            //collaborations = dto.collaborations,
            //orders = dto.orders,
            //socialNetworks = dto.social_networks.map { SocialNetwork(1,it,"") },
            tags = dto.tags,
            customization = EntrepreneurshipCustomization(
                id = UUID.fromString(dto.customization.id),
                profileImg = dto.customization.profile_img,
                bannerImg = dto.customization.banner_img,
                color1 = dto.customization.color1,
                color2 = dto.customization.color2
            )
        )
    }
}