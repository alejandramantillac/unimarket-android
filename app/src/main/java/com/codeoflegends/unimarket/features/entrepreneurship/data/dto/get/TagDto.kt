package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TagRelationDto (
    @SerializedName("Tags_id")
    val tagsId: TagDto
)

data class TagDto (
    val id: String,
    val name: String
)