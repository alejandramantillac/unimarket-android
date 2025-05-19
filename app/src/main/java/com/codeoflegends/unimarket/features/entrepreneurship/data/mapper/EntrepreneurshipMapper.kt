package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import android.util.Log
import com.codeoflegends.unimarket.core.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization

object EntrepreneurshipMapper {
    fun entrepreneurshipDtoToEntrepreneurship(dto: EntrepreneurshipDto): Entrepreneurship {
        return Entrepreneurship(
            id = dto.id,
            name = dto.name,
            slogan = dto.slogan,
            description = dto.description,
            creationDate = dto.creation_date,
            customization = EntrepreneurshipCustomization(
                id = dto.customization.id,
                profileImg = dto.customization.profile_img,
                bannerImg = dto.customization.banner_img,
                color1 = dto.customization.color1,
                color2 = dto.customization.color2
            ),
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
            socialNetworks = dto.social_networks,
            tags = dto.tags.mapNotNull { tagDto ->
                Log.d("EntrepreneurshipMapper", "Processing tag: id=${tagDto.id}, name=${tagDto.name}")
                tagDto.name?.let { name ->
                    Tag(
                        id = tagDto.id,
                        name = name
                    )
                }
            },
        )
    }
}