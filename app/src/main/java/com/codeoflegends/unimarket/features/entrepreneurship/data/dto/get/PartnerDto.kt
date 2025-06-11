package com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get

import java.util.UUID

data class PartnerDto(
    val id: UUID,
    val name: String,
    val role: String,
    val email: String,
    val entrepreneurship_id: UUID,
    val status: String,
    val date_created: String,
    val date_updated: String?
) 