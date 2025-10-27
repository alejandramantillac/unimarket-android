package com.codeoflegends.unimarket.features.auth.data.interceptor

import android.util.Log
import com.codeoflegends.unimarket.config.AuthInterceptor
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptorImpl @Inject constructor(
    private val tokenRepository: TokenRepository
) : AuthInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Log request details
        Log.d("AuthInterceptor", "Request URL: ${originalRequest.url}")
        Log.d("AuthInterceptor", "Request Method: ${originalRequest.method}")
        Log.d("AuthInterceptor", "Request Headers: ${originalRequest.headers}")
        
        if (originalRequest.body != null) {
            Log.d("AuthInterceptor", "Request has body: true")
        }

        if (isAuthenticationRequest(originalRequest)) {
            Log.d("AuthInterceptor", "Authentication request - proceeding without token")
            return chain.proceed(originalRequest)
        }

        val token = runBlocking { tokenRepository.getAccessToken() }
        Log.d("AuthInterceptor", "Token present: ${!token.isNullOrEmpty()}")
        
        if (!token.isNullOrEmpty()) {
            Log.d("AuthInterceptor", "🔑 Token (primeros 50 chars): ${token.take(50)}...")
            Log.d("AuthInterceptor", "🔑 Token (últimos 20 chars): ...${token.takeLast(20)}")
        }

        return if (token.isNullOrEmpty()) {
            Log.d("AuthInterceptor", "❌ No token available - proceeding without authentication")
            chain.proceed(originalRequest)
        } else {
            val authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            
            // Log the final request
            Log.d("AuthInterceptor", "✅ Adding token to request")
            Log.d("AuthInterceptor", "Authorization header: Bearer ${token.take(30)}...${token.takeLast(10)}")
            
            val response = chain.proceed(authenticatedRequest)
            
            // Log response details
            Log.d("AuthInterceptor", "📨 Response Code: ${response.code}")
            Log.d("AuthInterceptor", "📨 Response Message: ${response.message}")
            
            if (response.code == 401) {
                Log.e("AuthInterceptor", "🚨 401 UNAUTHORIZED! El token puede estar expirado o ser inválido")
                Log.e("AuthInterceptor", "🚨 URL que falló: ${originalRequest.url}")
            }
            
            response
        }
    }

    private fun isAuthenticationRequest(request: Request): Boolean {
        val url = request.url.toString()
        return url.contains("/auth/login") || url.contains("/auth/refresh")
    }
}