package com.codeoflegends.unimarket.features.auth.data.repositories.interfaces

interface RoleRepository {
    suspend fun ensureRolesCached()
    suspend fun fetchRolesFromApi(): Map<String, String>
    suspend fun cacheRoles(roles: Map<String, String>)
    suspend fun getRolName(rolId: String): String
}