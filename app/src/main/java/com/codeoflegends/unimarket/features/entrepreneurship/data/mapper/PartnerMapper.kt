package com.codeoflegends.unimarket.features.entrepreneurship.data.mapper

import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.PartnerDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun PartnerDto.toDomain(): Partner {
    return Partner(
        id = id,
        name = name,
        role = role,
        email = email,
        entrepreneurshipId = entrepreneurship_id,
        status = status,
        dateCreated = LocalDateTime.parse(date_created, DateTimeFormatter.ISO_DATE_TIME),
        dateUpdated = date_updated?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) }
    )
}

fun Partner.toDto(): PartnerDto {
    return PartnerDto(
        id = id,
        name = name,
        role = role,
        email = email,
        entrepreneurship_id = entrepreneurshipId,
        status = status,
        date_created = dateCreated.format(DateTimeFormatter.ISO_DATE_TIME),
        date_updated = dateUpdated?.format(DateTimeFormatter.ISO_DATE_TIME)
    )
} 