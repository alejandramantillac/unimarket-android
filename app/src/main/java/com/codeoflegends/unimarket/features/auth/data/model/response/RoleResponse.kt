package com.codeoflegends.unimarket.features.auth.data.model.response

import com.google.gson.annotations.SerializedName

data class RoleResponse(
    val data: List<RoleResponseData>,
)

data class RoleResponseData(
    @SerializedName("id")
    val id: String,

    @SerializedName("id")
    val name: String
)