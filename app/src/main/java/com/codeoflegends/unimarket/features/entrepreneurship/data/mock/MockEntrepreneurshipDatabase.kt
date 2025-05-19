package com.codeoflegends.unimarket.features.entrepreneurship.data.mock

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.*
import java.time.LocalDateTime
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipType
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import java.util.UUID
import java.time.LocalDateTime

object MockEntrepreneurshipDatabase {
    private val entrepreneurshipTypes = mapOf(
        1 to EntrepreneurshipType(1, "Tienda de Ropa"),
        2 to EntrepreneurshipType(2, "Restaurante"),
        3 to EntrepreneurshipType(3, "Servicios Digitales"),
        4 to EntrepreneurshipType(4, "Supermercado")
    )

    private val entrepreneurships = mutableMapOf(
        "1" to Entrepreneurship(
            id = UUID.randomUUID(),
            name = "Fresas con crema",
            slogan = "¡La mejor comida rápida de la ciudad!",
            description = "Fresas con crema es un negocio de comida rápida que ofrece fresas frescas y deliciosas con crema batida y otros ingredientes.",
            creationDate = LocalDateTime.now(),
            customization = null,
            email = "fresas@gmail.com",
            phone = "1234567890",
            subscription = UUID.randomUUID(),
            status = "active",
        UUID.fromString("11111111-1111-1111-1111-111111111111") to Entrepreneurship(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            name = "Fashion Store",
            slogan = "Tu estilo, nuestra pasión",
            description = "Tienda de ropa moderna y accesible",
            creationDate = "2025-04-19T12:00:00",
            email = "contact@fashionstore.com",
            phone = "+1234567890",
            status = "ACTIVE",
            category = 1,
            userFounder = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
            customization = EntrepreneurshipCustomization(
                id = UUID.fromString("11111111-0000-1111-1111-111111111112"),
                profileImg = "https://example.com/profile.jpg",
                bannerImg = "https://example.com/banner.jpg",
                color1 = "#FF5733",
                color2 = "#33FF57"
            )
            socialNetworks = listOf(
                SocialNetwork(2, "Facebook", "https://facebook.com/fresas"),
                SocialNetwork(1, "Instagram", "https://instagram.com/fresas")
            ),
            userFounder = UUID.randomUUID()
    ),
        "2" to Entrepreneurship(
            id = UUID.randomUUID(),
            name = "Tacos al pastor",
            slogan = "¡Los mejores tacos de la ciudad!",
            description = "Tacos al pastor es un negocio de comida rápida que ofrece tacos al pastor con carne de cerdo, piña y salsa.",
            creationDate = LocalDateTime.now(),
            customization = null,
            email = "taquitos@gmail.com",
            phone = "0987654321",
            subscription = UUID.randomUUID(),
            status = "active",
            category = 2,
            socialNetworks = listOf(
                SocialNetwork(2, "Facebook", "https://facebook.com/fresas"),
                SocialNetwork(1, "Instagram", "https://instagram.com/fresas")
            ),
            userFounder = UUID.randomUUID()
        ),
        UUID.fromString("22222222-2222-2222-2222-222222222222") to Entrepreneurship(
            id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            name = "Tech Solutions",
            slogan = "Innovación al alcance de todos",
            description = "Servicios de desarrollo y consultoría tecnológica",
            creationDate = "2025-04-19T12:00:00",
            email = "info@techsolutions.com",
            phone = "+1987654321",
            status = "ACTIVE",

        "3" to Entrepreneurship(
            id = UUID.randomUUID(),
            name = "Pizzas a la leña",
            slogan = "¡Las mejores pizzas de la ciudad!",
            description = "Pizzas a la leña es un negocio de comida rápida que ofrece pizzas al horno de leña con ingredientes frescos y de calidad.",
            creationDate = LocalDateTime.now(),
            customization = null,
            email = "pizzita@gmail.com",
            phone = "1122334455",
            subscription = UUID.randomUUID(),
            status = "active",
            category = 3,
            userFounder = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
            customization = EntrepreneurshipCustomization(
                id = UUID.fromString("11111111-0000-0000-1111-111111111112"),
                profileImg = "https://example.com/profile.jpg",
                bannerImg = "https://example.com/banner.jpg",
                color1 = "#FF5733",
                color2 = "#33FF57"
            )
        )
    )

    private val customizations = mutableMapOf(
        UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc") to EntrepreneurshipCustomization(
            id = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"),
            profileImg = "https://example.com/fashion-store-profile.jpg",
            bannerImg = "https://example.com/fashion-store-banner.jpg",
            color1 = "#FF5733",
            color2 = "#33FF57"
        ),
        UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd") to EntrepreneurshipCustomization(
            id = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"),
            profileImg = "https://example.com/tech-solutions-profile.jpg",
            bannerImg = "https://example.com/tech-solutions-banner.jpg",
            color1 = "#3357FF",
            color2 = "#FF33F6"
        )
            socialNetworks = listOf(
                SocialNetwork(2, "Facebook", "https://facebook.com/fresas"),
                SocialNetwork(1, "Instagram", "https://instagram.com/fresas")
            ),
            userFounder = UUID.randomUUID())
    )

    private val subscriptions = mutableMapOf(
        UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee") to EntrepreneurshipSubscription(
            id = UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee"),
            subscriptionPlan = UUID.fromString("11111111-1111-1111-1111-111111111112"),
            cutoffDate = LocalDateTime.now().plusMonths(1),
            lastPayment = LocalDateTime.now().minusMonths(1),
            entrepreneurship = UUID.fromString("11111111-1111-1111-1111-111111111111")
        ),
        UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff") to EntrepreneurshipSubscription(
            id = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"),
            subscriptionPlan = UUID.fromString("22222222-2222-2222-2222-222222222223"),
            cutoffDate = LocalDateTime.now().plusMonths(1),
            lastPayment = LocalDateTime.now().minusMonths(1),
            entrepreneurship = UUID.fromString("22222222-2222-2222-2222-222222222222")
        )
    )
    fun getEntrepreneurship(id: String): Entrepreneurship? = entrepreneurships[id]

    fun getEntrepreneurship(id: UUID): Entrepreneurship? = entrepreneurships[id]

    fun getAllEntrepreneurships(): List<Entrepreneurship> = entrepreneurships.values.toList()
    fun getAllEntrepreneurship(): List<Entrepreneurship> = entrepreneurships.values.toList()

    fun addEntrepreneurship(entrepreneurship: Entrepreneurship) {
        entrepreneurships[entrepreneurship.id ?: generateId()] = entrepreneurship
        entrepreneurships[entrepreneurship.id.toString()] = entrepreneurship
    }

    fun updateEntrepreneurship(entrepreneurship: Entrepreneurship) {
        entrepreneurship.id?.let { entrepreneurships[it] = entrepreneurship }
        entrepreneurships[entrepreneurship.id.toString()] = entrepreneurship
    }

    fun deleteEntrepreneurship(id: UUID) {

    fun deleteEntrepreneurship(id: String) {
        entrepreneurships.remove(id)
    }

    fun getCustomization(id: UUID): EntrepreneurshipCustomization? = customizations[id]

    fun addCustomization(customization: EntrepreneurshipCustomization) {
        customizations[customization.id ?: generateId()] = customization
    }

    fun updateCustomization(customization: EntrepreneurshipCustomization) {
        customization.id?.let { customizations[it] = customization }
    }

    fun getSubscription(id: UUID): EntrepreneurshipSubscription? = subscriptions[id]

    fun getSubscriptionByEntrepreneurship(entrepreneurshipId: UUID): EntrepreneurshipSubscription? =
        subscriptions.values.find { it.entrepreneurship == entrepreneurshipId }

    fun addSubscription(subscription: EntrepreneurshipSubscription) {
        subscriptions[subscription.id ?: generateId()] = subscription
    }

    fun updateSubscription(subscription: EntrepreneurshipSubscription) {
        subscription.id?.let { subscriptions[it] = subscription }
    }

    fun getEntrepreneurshipType(id: Int): EntrepreneurshipType? = entrepreneurshipTypes[id]

    fun getAllEntrepreneurshipTypes(): List<EntrepreneurshipType> = entrepreneurshipTypes.values.toList()

    private fun generateId(): UUID = UUID.randomUUID()
}
}