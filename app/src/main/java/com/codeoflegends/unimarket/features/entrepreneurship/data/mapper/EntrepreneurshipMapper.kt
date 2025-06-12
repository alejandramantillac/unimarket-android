package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.data.model.UserProfile
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
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipPartner
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.ProductPreview
import com.codeoflegends.unimarket.features.product.data.model.Product
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
            category = dto.category,
            deletedAt = dto.deletedAt,
            partners = dto.partners.map { partnerDto ->
                EntrepreneurshipPartner(
                    id = partnerDto.id,
                    role = partnerDto.role,
                    user = User(
                        id = UUID.fromString(partnerDto.user[0].id),
                        firstName = partnerDto.user[0].firstName,
                        lastName = partnerDto.user[0].lastName,
                        email = partnerDto.user[0].email,
                        profile = UserProfile(
                            profilePicture = partnerDto.user[0].profile?.profilePicture ?: "",
                            userRating = partnerDto.user[0].profile?.userRating ?: 0.0F,
                            partnerRating = partnerDto.user[0].profile?.partnerRating ?: 0.0F,
                            registrationDate = LocalDateTime.parse(
                                partnerDto.user[0].profile?.registrationDate ?: LocalDateTime.now().toString(),
                                DateTimeFormatter.ISO_DATE_TIME
                            )
                        )
                    )
                )
            },
            products = dto.products.map { productDto ->
                ProductPreview(
                    id = UUID.fromString(productDto.id),
                    name = productDto.name,
                    price = productDto.price,
                    imageUrl = productDto.image,
                )
            },
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