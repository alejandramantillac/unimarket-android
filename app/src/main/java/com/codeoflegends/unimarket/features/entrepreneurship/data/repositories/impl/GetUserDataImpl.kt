package com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.core.data.dto.UserDto
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.repositories.interfaces.IUserGetDataRepository
import com.codeoflegends.unimarket.features.entrepreneurship.service.UserGetDataService
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserDataImpl @Inject constructor(
    private val  getUserData: UserGetDataService,
    private val authRepository: AuthRepository
): IUserGetDataRepository {
    override suspend fun getUserData(): Result<UserDto> {
        return try {
            val query = UserDto.query().build()
            Log.d("UserProfile", "🌐 ====================================")
            Log.d("UserProfile", "🌐 Llamando al endpoint /users/me...")
            Log.d("UserProfile", "🌐 URL completa: http://34.132.14.74/users/me?fields=*,profile.*")
            Log.d("UserProfile", "🌐 Query enviado: $query")
            Log.d("UserProfile", "🌐 ====================================")
            
            val response = getUserData.getUserData()
            
            Log.d("UserProfile", "🌐 Respuesta completa del servidor:")
            Log.d("UserProfile", "🌐 Response JSON: ${Gson().toJson(response)}")
            Log.d("UserProfile", "🌐 User data: ${Gson().toJson(response.data)}")
            
            Result.success(response.data)
        } catch (e: Exception) {
            Log.e("UserProfile", "🌐 ====================================")
            Log.e("UserProfile", "🌐 ❌ ERROR 401 en URL:")
            Log.e("UserProfile", "🌐 ❌ GET http://34.132.14.74/users/me?fields=*,profile.*")
            Log.e("UserProfile", "🌐 ❌ Error: ${e.message}")
            Log.e("UserProfile", "🌐 ====================================")
            Log.e("UserProfile", "Stack trace:", e)
            Result.failure(e)
        }
    }
}