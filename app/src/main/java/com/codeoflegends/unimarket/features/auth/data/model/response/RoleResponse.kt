package com.codeoflegends.unimarket.features.auth.data.model.response

data class RoleResponse(
    val data: List<RoleResponseData>,
)

data class RoleResponseData(
    val id: String,
    val name: String
)