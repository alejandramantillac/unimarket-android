package com.codeoflegends.unimarket.core.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.codeoflegends.unimarket.core.data.service.ImageUploadService
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.min

/**
 * Use case for uploading images to the server
 */
class UploadImageUseCase @Inject constructor(
    private val imageUploadService: ImageUploadService,
    @ApplicationContext private val context: Context
) {
    /**
     * Uploads an image from a URI and returns the server URL
     * @param uri The local URI of the image to upload
     * @param customFileName Optional custom filename (without extension) to use for the upload
     * @return The URL of the uploaded image on the server
     */
    suspend operator fun invoke(uri: Uri, customFileName: String? = null): String {
        // Convert URI to File
        val file = uriToFile(uri)
        
        // Determine the filename to use
        val fileName = if (customFileName != null) {
            "$customFileName.jpg"
        } else {
            file.name
        }
        
        // Create multipart body part
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData(
            "image",
            fileName,
            requestBody
        )
        
        // Upload image
        val response = imageUploadService.uploadImage(part)
        
        // Clean up temporary file
        file.delete()
        
        // Parse response - could be JSON, plain text URL, or message
        val responseText = response.string().trim()
        
        // Try to parse as JSON first
        return try {
            when {
                // If it's JSON like {"url": "..."}
                responseText.startsWith("{") -> {
                    val urlPattern = """"url"\s*:\s*"([^"]+)"""".toRegex()
                    val match = urlPattern.find(responseText)
                    match?.groupValues?.get(1) ?: responseText
                }
                // If it's a message like "File {filename} uploaded."
                responseText.contains("File") && responseText.contains("uploaded") -> {
                    // Extract filename from message
                    val filenamePattern = """File\s+([^\s]+)\s+uploaded""".toRegex()
                    val match = filenamePattern.find(responseText)
                    val extractedFilename = match?.groupValues?.get(1)?.removeSuffix(".")
                    
                    // Build full URL with the base URL
                    if (extractedFilename != null) {
                        "https://storage.googleapis.com/unimarket-images/$extractedFilename"
                    } else {
                        responseText
                    }
                }
                // Assume it's already a URL
                responseText.startsWith("http") -> responseText
                // Otherwise, treat as filename and build URL
                else -> "https://unimarket-api-210876120903.us-central1.run.app/images/$responseText"
            }
        } catch (e: Exception) {
            android.util.Log.e("UploadImageUseCase", "Error parsing response: $responseText", e)
            // Fallback to treating entire response as URL
            responseText
        }
    }
    
    /**
     * Converts a URI to a temporary file with compression
     */
    private fun uriToFile(uri: Uri): File {
        val contentResolver = context.contentResolver
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        
        try {
            // Read the original image
            val originalBitmap = contentResolver.openInputStream(uri)?.use { input ->
                BitmapFactory.decodeStream(input)
            } ?: throw Exception("Could not read image from URI")
            
            // Calculate new dimensions (max 1920x1920 to reduce file size)
            val maxDimension = 1920
            val scale = min(
                maxDimension.toFloat() / originalBitmap.width,
                maxDimension.toFloat() / originalBitmap.height
            ).coerceAtMost(1.0f) // Don't upscale
            
            val newWidth = (originalBitmap.width * scale).toInt()
            val newHeight = (originalBitmap.height * scale).toInt()
            
            // Resize the bitmap if needed
            val resizedBitmap = if (scale < 1.0f) {
                Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)
            } else {
                originalBitmap
            }
            
            // Compress and save to temp file
            FileOutputStream(tempFile).use { output ->
                // Compress to JPEG with 85% quality (good balance between quality and size)
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, output)
            }
            
            // Clean up bitmaps
            if (resizedBitmap != originalBitmap) {
                resizedBitmap.recycle()
            }
            originalBitmap.recycle()
            
            android.util.Log.d("UploadImageUseCase", "Image compressed: ${tempFile.length() / 1024}KB")
            
        } catch (e: Exception) {
            android.util.Log.e("UploadImageUseCase", "Error compressing image, using original", e)
            // Fallback: copy original file without compression
            contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
        
        return tempFile
    }
}

