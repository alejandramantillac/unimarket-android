package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.core.dto.DeleteDto
import com.codeoflegends.unimarket.core.utils.DirectusQuery
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.dto.get.EntrepreneurshipListDto
import com.codeoflegends.unimarket.features.entrepreneurship.data.mapper.EntrepreneurshipMapper
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCreate
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.EntrepreneurshipService
import com.codeoflegends.unimarket.features.product.data.dto.get.ProductListDto
import com.codeoflegends.unimarket.features.product.data.mapper.ProductMapper
import com.codeoflegends.unimarket.features.product.data.model.Product
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntrepreneurshipRepositoryDirectus @Inject constructor(
    private val authRepository: AuthRepository,
    private val entrepreneurshipService: EntrepreneurshipService
) : IEntrepreneurshipRepository {
    override suspend fun createEntrepreneurship(entrepreneurship: EntrepreneurshipCreate): Result<Unit> =
        try {
            entrepreneurshipService.createEntrepreneurship(
                EntrepreneurshipMapper.toNewEntrepreneurshipDto(
                    entrepreneurship,
                    authRepository.getCurrentUserId()!!
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateEntrepreneurship(entrepreneurship: Entrepreneurship): Result<Unit> =
        try {
            entrepreneurshipService.updateEntrepreneurship(
                entrepreneurship.id!!,
                EntrepreneurshipMapper.toUpdateEntrepreneurshipDto(entrepreneurship)
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun deleteEntrepreneurship(entrepreneurshipId: UUID): Result<Unit> = try {
        val isoTimestamp = Instant.now().atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_INSTANT)

        entrepreneurshipService.deleteEntrepreneurship(
            entrepreneurshipId,
            DeleteDto(isoTimestamp)
        )

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getEntrepreneurship(entrepreneurshipId: UUID): Result<Entrepreneurship> =
        try {
            val entrepreneurshipDto = entrepreneurshipService.getEntrepreneurship(
                entrepreneurshipId.toString(),
                EntrepreneurshipDto.query().build()
            ).data
            val entrepreneurship =
                EntrepreneurshipMapper.entrepreneurshipDtoToEntrepreneurship(entrepreneurshipDto)
            Result.success(entrepreneurship)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getAllEntrepreneurships(): Result<List<Entrepreneurship>> = try {
        Log.d("EmprendimientosPropios", "*** Repository: Obteniendo ID del usuario actual ***")
        val currentUserId = authRepository.getCurrentUserId()
        Log.d("EmprendimientosPropios", "*** Repository: User ID = $currentUserId ***")
        
        if (currentUserId == null) {
            Log.e("EmprendimientosPropios", "*** Repository: ERROR - Usuario no autenticado (ID es null) ***")
        }
        
        Log.d("EmprendimientosPropios", "*** Repository: Construyendo query con filtro user_founder = $currentUserId ***")
        val queryBuilder = EntrepreneurshipDto.query()
        Log.d("EmprendimientosPropios", "*** Repository: Query base (antes de filtro): ${queryBuilder.build()} ***")
        
        val query = queryBuilder.filter("user_founder", "eq", currentUserId.toString()).build()
        
        Log.d("EmprendimientosPropios", "*** Repository: Query completo construido: $query ***")
        Log.d("EmprendimientosPropios", "*** Repository: Ejecutando llamada al servicio... ***")
        
        val response = entrepreneurshipService.getMyEntrepreneurships(query)
        val entrepreneurshipDtos = response.data
        
        Log.d("EmprendimientosPropios", "*** Repository: Respuesta recibida - ${entrepreneurshipDtos.size} DTOs ***")
        entrepreneurshipDtos.forEachIndexed { index, dto ->
            Log.d("EmprendimientosPropios", "*** Repository: DTO[$index] - ID: ${dto.id}, Name: ${dto.name}, Orders: ${dto.orders.size} ***")
        }
        
        val entrepreneurshipList = entrepreneurshipDtos.map { entrepreneurshipDto ->
            EntrepreneurshipMapper.entrepreneurshipDtoToEntrepreneurship(entrepreneurshipDto)
        }
        
        Log.d("EmprendimientosPropios", "*** Repository: ${entrepreneurshipList.size} emprendimientos mapeados exitosamente ***")
        Result.success(entrepreneurshipList)
    } catch (e: Exception) {
        Log.e("EmprendimientosPropios", "*** Repository: EXCEPCIÓN capturada ***")
        Log.e("EmprendimientosPropios", "*** Repository: Tipo: ${e.javaClass.simpleName} ***")
        Log.e("EmprendimientosPropios", "*** Repository: Mensaje: ${e.message} ***")
        Log.e("EmprendimientosPropios", "*** Repository: Stack trace: ***", e)
        Result.failure(e)
    }

    override suspend fun getAllEntrepreneurshipsByQuery(
        nameContains: String,
        filters: List<DirectusQuery.Filter>,
        limit: Int,
        page: Int
    ): Result<List<EntrepreneurshipListDto>> = try {
        Result.success(
            entrepreneurshipService.getEntrepreneurships(
                EntrepreneurshipListDto.queryByFilters(
                    nameContains = nameContains,
                    filters = filters,
                    limit = limit,
                    page = page
                ).build()
            ).data
        )
    } catch (e: Exception) {
        Log.e("ProductRepositoryDirectus", "Error fetching entrepreneurships by filters: ", e)
        Result.failure(e)
    }
}