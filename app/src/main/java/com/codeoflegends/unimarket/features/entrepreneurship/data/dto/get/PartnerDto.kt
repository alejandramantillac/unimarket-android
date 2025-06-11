package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import java.util.UUID

data class PartnerDto(
    val id: UUID,
    val role: String,
    val user: UserDto,
    val entrepreneurship: UUID
) 