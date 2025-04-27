package com.codeoflegends.unimarket.features.auth.data.repositories.impl

import com.codeoflegends.unimarket.config.DataStoreManager
import com.codeoflegends.unimarket.core.constant.PreferenceKeys
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.RoleRepository
import com.codeoflegends.unimarket.features.auth.data.service.AuthService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get

@Singleton
class RoleRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val authService: AuthService
) : RoleRepository {
    override suspend fun ensureRolesCached() {
        val cachedRoles = dataStoreManager.get(PreferenceKeys.ROLES).first()
        if (cachedRoles.isEmpty()) {
            val roles = fetchRolesFromApi()
            cacheRoles(roles)
        }
    }

    override suspend fun fetchRolesFromApi(): Map<String, String> {
        val response = authService.getRoles()
        return response.data.associate { it.id to it.name }
    }

    override suspend fun cacheRoles(roles: Map<String, String>) {
        val rolesJson = Gson().toJson(roles)
        dataStoreManager.save(PreferenceKeys.ROLES, rolesJson)
    }

    override suspend fun getRolName(rolId: String): String {
        ensureRolesCached()
        val cachedRolesJson = dataStoreManager.get(PreferenceKeys.ROLES).first()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val cachedRoles: Map<String, String> = Gson().fromJson(cachedRolesJson, type)
        return cachedRoles[rolId] ?: "undefined"
    }
}