package com.codeoflegends.unimarket.core.factory

import com.codeoflegends.unimarket.core.annotation.MainRetrofit
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceFactory @Inject constructor(
    @MainRetrofit val retrofit: Retrofit
) {
    inline fun <reified T> create(): T {
        return retrofit.create(T::class.java)
    }
}