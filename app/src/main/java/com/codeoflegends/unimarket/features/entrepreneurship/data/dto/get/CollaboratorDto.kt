package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.data.model.User
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import java.util.UUID

data class CollaboratorDto(
    val id: UUID,
    val role: String,
    val user: List<UserDto>
)

data class DirectusCollaboratorDto(
    val partners: List<CollaboratorDto>,
) {
    companion object {
        fun query(): DirectusQuery {
            return DirectusQuery()
                .join("partners")
                .join("partners.user")
                .join("partners.user.profile")
        }
    }
}