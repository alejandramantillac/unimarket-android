package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

data class EntrepreneurshipBasicFormUiState(
    val name: String = "",
    val slogan: String = "",
    val description: String = "",
    val email: String = "",
    val phone: String = "",
    val selectedCategory: EntrepreneurshipCategory? = null,
    val categories: List<EntrepreneurshipCategory> = emptyList(),
    val isCategoryExpanded: Boolean = false
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
    private val getEntrepreneurshipCategory: GetCategoryUseCase
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

    fun saveEntrepreneurship() {
        viewModelScope.launch {
            _actionState.value = EntrepreneurshipBasicFormActionState.Loading
            try {
                val entrepreneurship = EntrepreneurshipCreate(
                    name = state.value.name,
                    slogan = state.value.slogan,
                    description = state.value.description,
                    email = state.value.email,
                    phone = state.value.phone,
                    category = state.value.selectedCategory?.id ?: 0,
                    customization = EntrepreneurshipCustomization(
                        profileImg = "",
                        bannerImg = "",
                        color1 = "",
                        color2 = ""
                    ),
                )

                createEntrepreneurshipUseCase(entrepreneurship)
                _actionState.value = EntrepreneurshipBasicFormActionState.Success
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipBasicFormActionState.Error(e.message ?: "Error al guardar")
            }
        }
    }
}