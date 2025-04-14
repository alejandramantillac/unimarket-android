package com.codeoflegends.unimarket.features.auth.data.repositories.impl

import android.util.Log
import com.codeoflegends.unimarket.config.DataStoreManager
import com.codeoflegends.unimarket.core.constant.PreferenceKeys
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : TokenRepository {

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStoreManager.save(PreferenceKeys.ACCESS_TOKEN, accessToken)
        dataStoreManager.save(PreferenceKeys.REFRESH_TOKEN, refreshToken)
    }

    override suspend fun getAccessToken(): String? {
        return dataStoreManager.get(PreferenceKeys.ACCESS_TOKEN)
            .firstOrNull()
            .takeIf { !it.isNullOrEmpty() }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStoreManager.get(PreferenceKeys.REFRESH_TOKEN)
            .firstOrNull()
            .takeIf { !it.isNullOrEmpty() }
    }

    override suspend fun clearTokens() {
        dataStoreManager.save(PreferenceKeys.ACCESS_TOKEN, "")
        dataStoreManager.save(PreferenceKeys.REFRESH_TOKEN, "")
    }

    override fun getAccessTokenFlow(): Flow<String?> {
        return dataStoreManager.get(PreferenceKeys.ACCESS_TOKEN)
            .map { it.ifEmpty { null } }
    }

    override suspend fun hasValidToken(): Boolean {
        return getAccessToken() != null
    }
}