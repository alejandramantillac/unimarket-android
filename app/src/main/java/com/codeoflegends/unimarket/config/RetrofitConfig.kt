package com.codeoflegends.unimarket.config

import okhttp3.Authenticator
import okhttp3.Interceptor

object RetrofitConfig {
    const val URL = "http://34.132.14.74"
}

interface AuthInterceptor : Interceptor

interface TokenAuthenticator : Authenticator