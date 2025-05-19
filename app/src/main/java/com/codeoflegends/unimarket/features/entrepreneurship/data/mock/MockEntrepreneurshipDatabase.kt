package com.codeoflegends.unimarket.features.entrepreneurship.data.mock

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.core.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import java.time.LocalDateTime
import java.util.UUID

object MockEntrepreneurshipDatabase {
    private val entrepreneurships = mutableMapOf<UUID, Entrepreneurship>(
        UUID.fromString("11111111-1111-1111-1111-111111111111") to Entrepreneurship(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            name = "Fashion Store",
            slogan = "Tu estilo, nuestra pasión",
            description = "Tienda de ropa moderna y accesible",
            creationDate = LocalDateTime.now(),
            email = "contact@fashionstore.com",
            phone = "+1234567890",
            subscription = null,
            status = "ACTIVE",
            category = 1,
            userFounder = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
            deletedAt = null,
            partners = listOf(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")),
            products = listOf(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc")),
            collaborations = emptyList(),
            orders = emptyList(),
            socialNetworks = listOf(SocialNetwork(id = 1, platform = "Instagram", url = ""),
                SocialNetwork(id = 2, platform = "TikTok", url = "")),
            tags = listOf(Tag(1, "moda"), Tag(2, "accesible")),
            customization = EntrepreneurshipCustomization(
                id = UUID.fromString("11111111-1111-1111-1111-111111111112"),
                profileImg = "https://example.com/profile.jpg",
                bannerImg = "https://example.com/banner.jpg",
                color1 = "#FF5733",
                color2 = "#C70039"
            )
        ),
        UUID.fromString("22222222-2222-2222-2222-222222222222") to Entrepreneurship(
            id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            name = "Tech Solutions",
            slogan = "Innovación al alcance de todos",
            description = "Servicios de desarrollo y consultoría tecnológica",
            creationDate = LocalDateTime.now(),
            email = "info@techsolutions.com",
            phone = "+1987654321",
            subscription = null,
            status = "ACTIVE",
            category = 3,
            userFounder = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
            deletedAt = null,
            partners = emptyList(),
            products = emptyList(),
            collaborations = emptyList(),
            orders = emptyList(),
            socialNetworks = listOf(SocialNetwork(id = 1, platform = "Instagram", url = ""),
                SocialNetwork(id = 2, platform = "TikTok", url = "")),
            tags = listOf(Tag(3,"tecnología"), Tag(4, "consultoría")),
            customization = EntrepreneurshipCustomization(
                id = UUID.fromString("22222222-2222-2222-2222-222222222223"),
                profileImg = "https://example.com/profile.jpg",
                bannerImg = "https://example.com/banner.jpg",
                color1 = "#33FF57",
                color2 = "#39C700"
            )
        )
    )

    fun getEntrepreneurship(id: UUID): Entrepreneurship? = entrepreneurships[id]

    fun getAllEntrepreneurships(): List<Entrepreneurship> = entrepreneurships.values.toList()

    fun addEntrepreneurship(entrepreneurship: Entrepreneurship) {
        val id = entrepreneurship.id ?: generateId()
        entrepreneurships[id] = entrepreneurship.copy(id = id)
    }

    fun updateEntrepreneurship(entrepreneurship: Entrepreneurship) {
        val id = entrepreneurship.id ?: return
        entrepreneurships[id] = entrepreneurship
    }

    fun deleteEntrepreneurship(id: UUID) {
        entrepreneurships.remove(id)
    }

    private fun generateId(): UUID = UUID.randomUUID()
}