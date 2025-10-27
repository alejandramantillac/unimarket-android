package com.codeoflegends.unimarket.features.entrepreneurship.data.usecase

import android.util.Log
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IEntrepreneurshipRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import javax.inject.Inject

class GetAllEntrepreneurship @Inject constructor(
    private val repository: IEntrepreneurshipRepository
) {
    suspend operator fun invoke(): List<Entrepreneurship> {
        Log.d("EmprendimientosPropios", "--- UseCase: Llamando al repository.getAllEntrepreneurships() ---")
        val result = repository.getAllEntrepreneurships()
        
        if (result.isSuccess) {
            val list = result.getOrThrow()
            Log.d("EmprendimientosPropios", "--- UseCase: Repository retornó ${list.size} emprendimientos ---")
            return list
        } else {
            Log.e("EmprendimientosPropios", "--- UseCase: Repository retornó un error ---")
            throw result.exceptionOrNull() ?: Exception("Error desconocido")
        }
    }
}