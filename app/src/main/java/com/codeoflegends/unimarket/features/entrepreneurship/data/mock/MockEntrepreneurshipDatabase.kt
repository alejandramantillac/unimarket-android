package com.codeoflegends.unimarket.features.entrepreneurship.data.mock

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipType
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import java.util.UUID
import java.time.LocalDateTime

object MockEntrepreneurshipDatabase {

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
            category = 1,
            socialNetworks = 1,
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
            socialNetworks = 2,
            userFounder = UUID.randomUUID()
        ),

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
            socialNetworks = 3,
            userFounder = UUID.randomUUID())
    )

    fun getEntrepreneurship(id: String): Entrepreneurship? = entrepreneurships[id]

    fun getAllEntrepreneurship(): List<Entrepreneurship> = entrepreneurships.values.toList()

    fun addEntrepreneurship(entrepreneurship: Entrepreneurship) {
        entrepreneurships[entrepreneurship.id.toString()] = entrepreneurship
    }

    fun updateEntrepreneurship(entrepreneurship: Entrepreneurship) {
        entrepreneurships[entrepreneurship.id.toString()] = entrepreneurship
    }

    fun deleteEntrepreneurship(id: String) {
        entrepreneurships.remove(id)
    }
}