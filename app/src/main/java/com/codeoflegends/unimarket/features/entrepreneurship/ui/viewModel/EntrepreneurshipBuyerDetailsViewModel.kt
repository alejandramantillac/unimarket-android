package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipRatingUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetAllEntrepreneurshipReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipBasicDetailsActionState {
    object Idle : EntrepreneurshipBasicDetailsActionState()
    object Success : EntrepreneurshipBasicDetailsActionState()
    data class Error(val message: String) : EntrepreneurshipBasicDetailsActionState()
    object Loading : EntrepreneurshipBasicDetailsActionState()
}

sealed class EntrepreneurshipReviewsActionState {
    object Idle : EntrepreneurshipReviewsActionState()
    data class Error(val message: String) : EntrepreneurshipReviewsActionState()
    object Loading : EntrepreneurshipReviewsActionState()
}

@HiltViewModel
class EntrepreneurshipBasicDetailsViewModel @Inject constructor(
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase,
    private val getEntrepreneurshipRatingUseCase: GetEntrepreneurshipRatingUseCase,
) : ViewModel() {
    // UI states
    private val _entrepreneurshipUiState = MutableStateFlow(EntrepreneurshipBasicUiState())
    val entrepreneurshipUiState: StateFlow<EntrepreneurshipBasicUiState> = _entrepreneurshipUiState.asStateFlow()

    private val _productsUiState = MutableStateFlow(EntrepreneurshipProductsUiState())
    val productsUiState: StateFlow<EntrepreneurshipProductsUiState> = _productsUiState.asStateFlow()

    private val _partnersUiState = MutableStateFlow(EntrepreneurshipPartnersUiState())
    val partnersUiState: StateFlow<EntrepreneurshipPartnersUiState> = _partnersUiState.asStateFlow()

    private val _reviewsUiState = MutableStateFlow(EntrepreneurshipReviewsUiState())
    val reviewsUiState: StateFlow<EntrepreneurshipReviewsUiState> = _reviewsUiState.asStateFlow()

    // Action states
    private val _actionState = MutableStateFlow<EntrepreneurshipBasicDetailsActionState>(EntrepreneurshipBasicDetailsActionState.Loading)
    val actionState: StateFlow<EntrepreneurshipBasicDetailsActionState> = _actionState.asStateFlow()

    private val _reviewsActionState = MutableStateFlow<EntrepreneurshipReviewsActionState>(EntrepreneurshipReviewsActionState.Idle)
    val reviewsActionState: StateFlow<EntrepreneurshipReviewsActionState> = _reviewsActionState.asStateFlow()


    fun loadEntrepreneurshipDetails(entrepreneurshipId: UUID) {
        _actionState.value = EntrepreneurshipBasicDetailsActionState.Loading

        viewModelScope.launch {
            try {
                Log.d("EntrepreneurshipDetails", "Loading entrepreneurship with ID: $entrepreneurshipId")
                
                val entrepreneurship = getEntrepreneurshipUseCase(entrepreneurshipId)
                Log.d("EntrepreneurshipDetails", "Entrepreneurship loaded successfully: ${entrepreneurship.name}")
                
                _entrepreneurshipUiState.value = _entrepreneurshipUiState.value.copy(
                    id = entrepreneurshipId,
                    name = entrepreneurship.name,
                    slogan = entrepreneurship.slogan,
                    description = entrepreneurship.description,
                    creationDate = entrepreneurship.creationDate,
                    customization = entrepreneurship.customization,
                    email = entrepreneurship.email,
                    phone = entrepreneurship.phone,
                    tags = entrepreneurship.tags
                )

                // Assign partners (take first 5)
                _partnersUiState.value = _partnersUiState.value.copy(
                    partners = entrepreneurship.partners.take(5)
                )
                Log.d("EntrepreneurshipDetails", "Partners assigned: ${entrepreneurship.partners.size} total, showing ${_partnersUiState.value.partners.size}")

                val reviewRating = getEntrepreneurshipRatingUseCase(entrepreneurshipId)
                _reviewsUiState.value = _reviewsUiState.value.copy(
                    averageRating = reviewRating.avgReview,
                    totalReviews = reviewRating.totalReviews
                )
                
                _actionState.value = EntrepreneurshipBasicDetailsActionState.Idle
                Log.d("EntrepreneurshipDetails", "All data loaded successfully")
            } catch (e: Exception) {
                Log.e("EntrepreneurshipDetails", "Error loading entrepreneurship: ${e.message}", e)
                _actionState.value = EntrepreneurshipBasicDetailsActionState.Error("Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }
}