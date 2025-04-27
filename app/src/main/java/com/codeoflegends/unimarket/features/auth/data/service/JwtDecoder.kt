package com.codeoflegends.unimarket.features.auth.data.service

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtDecoder @Inject constructor() {
    private val gson = Gson()

    suspend fun decodePayload(token: String): JwtPayload {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid JWT format")

        val payloadJson = String(
            Base64.decode(parts[1], Base64.URL_SAFE),
            Charsets.UTF_8
        )

        val payload = gson.fromJson(payloadJson, JwtPayload::class.java)

        return payload
    }

    data class JwtPayload(
        @SerializedName("id")
        val userId: String = "",

        @SerializedName("role")
        val userRole: String = ""
    )

}