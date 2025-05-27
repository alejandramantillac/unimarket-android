package com.codeoflegends.unimarket.config

import okhttp3.Authenticator
import okhttp3.Interceptor

object RetrofitConfig {
    const val URL = "http://35.231.238.114"
}

interface AuthInterceptor : Interceptor

interface TokenAuthenticator : Authenticator