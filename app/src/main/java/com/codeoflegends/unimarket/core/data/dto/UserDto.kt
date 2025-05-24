package com.codeoflegends.unimarket.core.data.dto

import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profile: UserProfileDto
){
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("profile")
        }
    }
}