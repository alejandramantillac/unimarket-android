package com.codeoflegends.unimarket.core.dto

import com.google.gson.annotations.SerializedName

data class DirectusDto<T>(
    val data: T
)

data class DeleteDto(
    @SerializedName("deleted_at") val deletedAt: String? = null
)