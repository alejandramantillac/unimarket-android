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
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SimpleOrder
import com.codeoflegends.unimarket.features.order.data.model.OrderStatus
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

object EntrepreneurshipMapper {
    fun entrepreneurshipDtoToEntrepreneurship(dto: EntrepreneurshipDto): Entrepreneurship {
        // Log temporal para debugging
        android.util.Log.d("EntrepreneurshipMapper", "=== Mapeando emprendimiento: ${dto.name} ===")
        android.util.Log.d("EntrepreneurshipMapper", "Orders recibidas del backend: ${dto.orders.size}")
        dto.orders.forEachIndexed { index, order ->
            android.util.Log.d("EntrepreneurshipMapper", "  Order[$index]: id=${order.id}, status.id=${order.status?.id}, status.name=${order.status?.name}")
        }
        
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
                val firstUser = partnerDto.user?.firstOrNull()
                EntrepreneurshipPartner(
                    id = partnerDto.id,
                    role = partnerDto.role ?: "",
                    user = if (firstUser != null) {
                        User(
                            id = UUID.fromString(firstUser.id),
                            firstName = firstUser.firstName ?: "",
                            lastName = firstUser.lastName ?: "",
                            email = firstUser.email ?: "",
                            profile = UserProfile(
                                profilePicture = firstUser.profile?.profilePicture ?: "",
                                userRating = firstUser.profile?.userRating ?: 0.0F,
                                partnerRating = firstUser.profile?.partnerRating ?: 0.0F,
                                registrationDate = LocalDateTime.parse(
                                    firstUser.profile?.registrationDate ?: LocalDateTime.now().toString(),
                                    DateTimeFormatter.ISO_DATE_TIME
                                )
                            )
                        )
                    } else {
                        User(
                            id = UUID.randomUUID(),
                            firstName = "",
                            lastName = "",
                            email = "",
                            profile = UserProfile(
                                profilePicture = "",
                                userRating = 0.0F,
                                partnerRating = 0.0F,
                                registrationDate = LocalDateTime.now()
                            )
                        )
                    }
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
            orders = dto.orders.mapNotNull { orderDto ->
                // Solo crear SimpleOrder si tenemos status válido (el id puede ser null)
                if (orderDto.status != null) {
                    try {
                        SimpleOrder(
                            id = orderDto.id,
                            status = OrderStatus(
                                id = orderDto.status.id,
                                name = orderDto.status.name
                            )
                        )
                    } catch (e: Exception) {
                        android.util.Log.w("EntrepreneurshipMapper", "Error mapeando orden: ${e.message}")
                        null
                    }
                } else {
                    android.util.Log.w("EntrepreneurshipMapper", "Orden sin status encontrada, ignorando")
                    null
                }
            },
            //socialNetworks = dto.social_networks.map { SocialNetwork(1,it,"") },
            tags = dto.tags.map {
                Tag(
                    id = UUID.fromString(it.tagsId.id),
                    name = it.tagsId.name
                )
            },
             customization = EntrepreneurshipCustomization(
                id = UUID.fromString(dto.customization.id),
                profileImg = dto.customization.profile_img ?: "",
                bannerImg = dto.customization.banner_img ?: "",
                color1 = dto.customization.color1 ?: "#000000",
                color2 = dto.customization.color2 ?: "#FFFFFF"
            )
        )
    }

    fun toNewEntrepreneurshipDto(entrepreneurship: EntrepreneurshipCreate, userId: UUID): NewEntrepreneurshipDto {
        return NewEntrepreneurshipDto(
            name = entrepreneurship.name,
            slogan = entrepreneurship.slogan,
            description = entrepreneurship.description,
            email = entrepreneurship.email,
            phone = entrepreneurship.phone,
            category = entrepreneurship.category,
            userFounder = userId,
            customization = NewEntrepreneurshipCustomizationDto(
                profileImg = entrepreneurship.customization.profileImg,
                bannerImg = entrepreneurship.customization.bannerImg,
                color1 = entrepreneurship.customization.color1,
                color2 = entrepreneurship.customization.color2
            )
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