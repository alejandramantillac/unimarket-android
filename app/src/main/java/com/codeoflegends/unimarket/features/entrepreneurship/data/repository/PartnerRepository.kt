package com.codeoflegends.unimarket.features.entrepreneurship.data.repository

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import java.util.UUID

interface PartnerRepository {
    suspend fun getPartnersByEntrepreneurship(entrepreneurshipId: UUID): List<Partner>
    suspend fun getPartnerById(id: UUID): Partner
    suspend fun createPartner(partner: Partner): Partner
    suspend fun updatePartner(id: UUID, partner: Partner): Partner
    suspend fun deletePartner(id: UUID)
} 