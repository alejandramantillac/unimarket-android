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

        return if (token.isNullOrEmpty()) {
            Log.d("AuthInterceptor", "No token available - proceeding without authentication")
            chain.proceed(originalRequest)
        } else {
            val authenticatedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            
            // Log the final request
            Log.d("AuthInterceptor", "Final request headers: ${authenticatedRequest.headers}")
            
            val response = chain.proceed(authenticatedRequest)
            
            // Log response details
            Log.d("AuthInterceptor", "Response Code: ${response.code}")
            Log.d("AuthInterceptor", "Response Message: ${response.message}")
            Log.d("AuthInterceptor", "Response Headers: ${response.headers}")
            
            response
        }
    }

    private fun isAuthenticationRequest(request: Request): Boolean {
        val url = request.url.toString()
        return url.contains("/auth/login") || url.contains("/auth/refresh")
    }
}