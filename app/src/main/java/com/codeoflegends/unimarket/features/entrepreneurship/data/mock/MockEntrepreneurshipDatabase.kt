package com.codeoflegends.unimarket.features.entrepreneurship.data.mock

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.*
import java.time.LocalDateTime
import java.util.UUID

object MockEntrepreneurshipDatabase {
    private val entrepreneurshipTypes = mapOf(
        1 to EntrepreneurshipType(1, "Tienda de Ropa"),
        2 to EntrepreneurshipType(2, "Restaurante"),
        3 to EntrepreneurshipType(3, "Servicios Digitales"),
        4 to EntrepreneurshipType(4, "Supermercado")
    )

    private val entrepreneurships = mutableMapOf(
        UUID.fromString("11111111-1111-1111-1111-111111111111") to Entrepreneurship(
            id = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            name = "Fashion Store",
            slogan = "Tu estilo, nuestra pasión",
            description = "Tienda de ropa moderna y accesible",
            creationDate = LocalDateTime.now().minusMonths(6),
            email = "contact@fashionstore.com",
            phone = "+1234567890",
            status = "ACTIVE",
            category = 1,
            userFounder = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        ),
        UUID.fromString("22222222-2222-2222-2222-222222222222") to Entrepreneurship(
            id = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            name = "Tech Solutions",
            slogan = "Innovación al alcance de todos",
            description = "Servicios de desarrollo y consultoría tecnológica",
            creationDate = LocalDateTime.now().minusMonths(3),
            email = "info@techsolutions.com",
            phone = "+1987654321",
            status = "ACTIVE",
            category = 3,
            userFounder = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
        )
    )

    private val customizations = mutableMapOf(
        UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc") to EntrepreneurshipCustomization(
            id = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"),
            profileImg = "https://example.com/fashion-store-profile.jpg",
            bannerImg = "https://example.com/fashion-store-banner.jpg",
            color1 = "#FF5733",
            color2 = "#33FF57",
            entrepreneurship = UUID.fromString("11111111-1111-1111-1111-111111111111")
        ),
        UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd") to EntrepreneurshipCustomization(
            id = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"),
            profileImg = "https://example.com/tech-solutions-profile.jpg",
            bannerImg = "https://example.com/tech-solutions-banner.jpg",
            color1 = "#3357FF",
            color2 = "#FF33F6",
            entrepreneurship = UUID.fromString("22222222-2222-2222-2222-222222222222")
        )
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

    private val tags = mutableListOf(
        EntrepreneurshipTags(
            entrepreneurshipId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            tagsId = UUID.fromString("11111111-1111-1111-1111-111111111113")
        ),
        EntrepreneurshipTags(
            entrepreneurshipId = UUID.fromString("11111111-1111-1111-1111-111111111111"),
            tagsId = UUID.fromString("11111111-1111-1111-1111-111111111114")
        ),
        EntrepreneurshipTags(
            entrepreneurshipId = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            tagsId = UUID.fromString("22222222-2222-2222-2222-222222222224")
        ),
        EntrepreneurshipTags(
            entrepreneurshipId = UUID.fromString("22222222-2222-2222-2222-222222222222"),
            tagsId = UUID.fromString("22222222-2222-2222-2222-222222222225")
        )
    )

    fun getEntrepreneurship(id: UUID): Entrepreneurship? = entrepreneurships[id]

    fun getAllEntrepreneurships(): List<Entrepreneurship> = entrepreneurships.values.toList()

    fun addEntrepreneurship(entrepreneurship: Entrepreneurship) {
        entrepreneurships[entrepreneurship.id ?: generateId()] = entrepreneurship
    }
    
    fun updateEntrepreneurship(entrepreneurship: Entrepreneurship) {
        entrepreneurship.id?.let { entrepreneurships[it] = entrepreneurship }
    }
    
    fun deleteEntrepreneurship(id: UUID) {
        entrepreneurships.remove(id)
    }

    fun getCustomization(id: UUID): EntrepreneurshipCustomization? = customizations[id]
    
    fun getCustomizationByEntrepreneurship(entrepreneurshipId: UUID): EntrepreneurshipCustomization? =
        customizations.values.find { it.entrepreneurship == entrepreneurshipId }
    
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

    fun getTagsByEntrepreneurship(entrepreneurshipId: UUID): List<EntrepreneurshipTags> =
        tags.filter { it.entrepreneurshipId == entrepreneurshipId }
    
    fun addTag(tag: EntrepreneurshipTags) {
        tags.add(tag)
    }
    
    fun removeTag(entrepreneurshipId: UUID, tagId: UUID) {
        tags.removeIf { it.entrepreneurshipId == entrepreneurshipId && it.tagsId == tagId }
    }

    private fun generateId(): UUID = UUID.randomUUID()
} 