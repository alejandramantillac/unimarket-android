package com.codeoflegends.unimarket.features.entrepreneurship.domain.usecase

import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Partner
import com.codeoflegends.unimarket.features.entrepreneurship.data.repository.PartnerRepository
import java.util.UUID
import javax.inject.Inject

class GetPartnersByEntrepreneurshipUseCase @Inject constructor(
    private val repository: PartnerRepository
) {
    suspend operator fun invoke(entrepreneurshipId: UUID): List<Partner> {
        return repository.getPartnersByEntrepreneurship(entrepreneurshipId)
    }
} 