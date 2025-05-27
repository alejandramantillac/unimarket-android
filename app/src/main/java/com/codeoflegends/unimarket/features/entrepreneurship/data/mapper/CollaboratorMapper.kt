package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.CollaboratorDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Collaborator

fun CollaboratorDto.toDomain(): Collaborator {
    return Collaborator(
        id = id,
        name = name,
        role = role,
        email = email,
        entrepreneurshipId = entrepreneurshipId
    )
}

fun Collaborator.toDto(): CollaboratorDto {
    return CollaboratorDto(
        id = id,
        name = name,
        role = role,
        email = email,
        entrepreneurshipId = entrepreneurshipId
    )
} 