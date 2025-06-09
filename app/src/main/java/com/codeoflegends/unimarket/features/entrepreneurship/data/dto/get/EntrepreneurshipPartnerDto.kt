package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.core.data.model.User
import java.util.UUID

data class EntrepreneurshipPartnerDto(
    val id: UUID,
    val role: String,
    val user: List<UserDto>
)