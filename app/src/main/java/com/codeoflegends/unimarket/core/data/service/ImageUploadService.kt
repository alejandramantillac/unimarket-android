package com.codeoflegends.unimarket.core.data.service

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Service for uploading images to the server
 */
interface ImageUploadService {
    @Multipart
    @POST("/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): ResponseBody
}

