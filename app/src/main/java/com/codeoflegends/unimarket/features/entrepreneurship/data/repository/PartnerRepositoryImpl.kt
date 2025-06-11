package com.codeoflegends.unimarket.features.entrepreneurship.data.repository

import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDomain
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.toDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import com.codeoflegends.unimarket.features.entrepreneurship.data.remote.PartnerService
import java.util.UUID
import javax.inject.Inject

class PartnerRepositoryImpl @Inject constructor(
    private val service: PartnerService
) : PartnerRepository {
    override suspend fun getPartnersByEntrepreneurship(entrepreneurshipId: UUID): List<Partner> {
        return service.getPartnersByEntrepreneurship(entrepreneurshipId).data.map { it.toDomain() }
    }

    override suspend fun getPartnerById(id: UUID): Partner {
        return service.getPartnerById(id).data.toDomain()
    }

    override suspend fun createPartner(partner: Partner): Partner {
        return service.createPartner(partner.toDto()).data.toDomain()
    }

    override suspend fun updatePartner(id: UUID, partner: Partner): Partner {
        return service.updatePartner(id, partner.toDto()).data.toDomain()
    }

    override suspend fun deletePartner(id: UUID) {
        service.deletePartner(id)
    }
} 