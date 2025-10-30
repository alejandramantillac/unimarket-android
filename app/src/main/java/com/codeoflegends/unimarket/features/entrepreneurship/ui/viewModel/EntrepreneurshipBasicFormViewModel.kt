package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.usecase.UploadImageUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCategory
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCreate
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.CreateEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

data class EntrepreneurshipBasicFormUiState(
    val name: String = "",
    val slogan: String = "",
    val description: String = "",
    val email: String = "",
    val phone: String = "",
    val selectedCategory: EntrepreneurshipCategory? = null,
    val categories: List<EntrepreneurshipCategory> = emptyList(),
    val isCategoryExpanded: Boolean = false,
    val bannerImageUri: Uri? = null,
    val profileImageUri: Uri? = null,
    val uploadProgress: String = ""
)

sealed class EntrepreneurshipBasicFormActionState {
    object Idle : EntrepreneurshipBasicFormActionState()
    object Success : EntrepreneurshipBasicFormActionState()
    data class Error(val message: String) : EntrepreneurshipBasicFormActionState()
    object Loading : EntrepreneurshipBasicFormActionState()
}

@HiltViewModel
class EntrepreneurshipBasicFormViewModel @Inject constructor(
    private val createEntrepreneurshipUseCase: CreateEntrepreneurshipUseCase,
    private val getEntrepreneurshipCategory: GetCategoryUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EntrepreneurshipBasicFormUiState())
    val state: StateFlow<EntrepreneurshipBasicFormUiState> = _state.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipBasicFormActionState>(EntrepreneurshipBasicFormActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipBasicFormActionState> = _actionState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getEntrepreneurshipCategory()
                _state.value = _state.value.copy(categories = categories)
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipBasicFormActionState.Error(e.message ?: "Error al cargar categorías")
            }
        }
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun updateSlogan(slogan: String) {
        _state.value = _state.value.copy(slogan = slogan)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun updatePhone(phone: String) {
        _state.value = _state.value.copy(phone = phone)
    }

    fun updateSelectedCategory(category: EntrepreneurshipCategory) {
        _state.value = _state.value.copy(selectedCategory = category)
    }

    fun toggleCategoryExpanded() {
        _state.value = _state.value.copy(isCategoryExpanded = !_state.value.isCategoryExpanded)
    }

    fun getFilteredCategories(searchQuery: String): List<EntrepreneurshipCategory> {
        return _state.value.categories.filter { 
            it.name.contains(searchQuery, ignoreCase = true) 
        }
    }

    fun updateBannerImage(uri: Uri?) {
        _state.value = _state.value.copy(bannerImageUri = uri)
    }

    fun updateProfileImage(uri: Uri?) {
        _state.value = _state.value.copy(profileImageUri = uri)
    }

    fun saveEntrepreneurship() {
        viewModelScope.launch {
            _actionState.value = EntrepreneurshipBasicFormActionState.Loading
            try {
                val entrepreneurshipId = UUID.randomUUID()
                
                // Subir imágenes si están seleccionadas
                var bannerUrl = ""
                var profileUrl = ""

                state.value.bannerImageUri?.let { uri ->
                    try {
                        _state.value = _state.value.copy(uploadProgress = "Subiendo imagen de banner...")
                        Log.d("EntrepreneurshipForm", "Uploading banner...")
                        bannerUrl = uploadImageUseCase(uri, "${entrepreneurshipId}_banner")
                        Log.d("EntrepreneurshipForm", "Banner uploaded: $bannerUrl")
                    } catch (e: Exception) {
                        Log.e("EntrepreneurshipForm", "Error uploading banner: ${e.message}", e)
                        _state.value = _state.value.copy(uploadProgress = "")
                        throw Exception("Error al subir imagen de banner. Verifica tu conexión e intenta nuevamente.")
                    }
                }

                state.value.profileImageUri?.let { uri ->
                    try {
                        _state.value = _state.value.copy(uploadProgress = "Subiendo imagen de perfil...")
                        Log.d("EntrepreneurshipForm", "Uploading profile...")
                        profileUrl = uploadImageUseCase(uri, "${entrepreneurshipId}_profile")
                        Log.d("EntrepreneurshipForm", "Profile uploaded: $profileUrl")
                    } catch (e: Exception) {
                        Log.e("EntrepreneurshipForm", "Error uploading profile: ${e.message}", e)
                        _state.value = _state.value.copy(uploadProgress = "")
                        throw Exception("Error al subir imagen de perfil. Verifica tu conexión e intenta nuevamente.")
                    }
                }

                _state.value = _state.value.copy(uploadProgress = "Creando emprendimiento...")

                val entrepreneurship = EntrepreneurshipCreate(
                    name = state.value.name,
                    slogan = state.value.slogan,
                    description = state.value.description,
                    email = state.value.email,
                    phone = state.value.phone,
                    category = state.value.selectedCategory?.id ?: 0,
                    customization = EntrepreneurshipCustomization(
                        profileImg = profileUrl,
                        bannerImg = bannerUrl,
                        color1 = "#6200EE",
                        color2 = "#03DAC5"
                    ),
                )

                createEntrepreneurshipUseCase(entrepreneurship)
                _state.value = _state.value.copy(uploadProgress = "")
                _actionState.value = EntrepreneurshipBasicFormActionState.Success
            } catch (e: Exception) {
                Log.e("EntrepreneurshipForm", "Error saving entrepreneurship", e)
                _state.value = _state.value.copy(uploadProgress = "")
                _actionState.value = EntrepreneurshipBasicFormActionState.Error(e.message ?: "Error al guardar")
            }
        }
    }
}