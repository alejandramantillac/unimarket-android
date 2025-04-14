package com.codeoflegends.unimarket.config

import okhttp3.Authenticator
import okhttp3.Interceptor

object RetrofitConfig {
    const val URL = "http://192.168.10.19:8055"
}

interface AuthInterceptor : Interceptor

interface TokenAuthenticator : Authenticator