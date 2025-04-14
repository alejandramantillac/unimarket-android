package com.codeoflegends.unimarket.features.auth.data.service

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtDecoder @Inject constructor() {
    private val gson = Gson()

    fun decodePermissions(token: String): List<String> {
        return try {
            val payload = decodePayload(token)
            payload.permissions
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun decodePayload(token: String): JwtPayload {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid JWT format")

        val payloadJson = String(
            Base64.decode(parts[1], Base64.URL_SAFE),
            Charsets.UTF_8
        )
        return gson.fromJson(payloadJson, JwtPayload::class.java)
    }

    private data class JwtPayload(
        @SerializedName("permissions")
        val permissions: List<String> = emptyList()
    )
}