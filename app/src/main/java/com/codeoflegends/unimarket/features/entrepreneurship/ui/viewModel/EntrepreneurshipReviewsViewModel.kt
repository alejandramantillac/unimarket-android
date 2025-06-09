package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipRatingUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetAllEntrepreneurshipReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipReviewsActionState {
    object Idle : EntrepreneurshipReviewsActionState()
    data class Error(val message: String) : EntrepreneurshipReviewsActionState()
    object Loading : EntrepreneurshipReviewsActionState()
}

@HiltViewModel
class EntrepreneurshipReviewsViewModel @Inject constructor(
    private val getAllEntrepreneurshipReviewsUseCase: GetAllEntrepreneurshipReviewsUseCase,
    private val getEntrepreneurshipRatingUseCase: GetEntrepreneurshipRatingUseCase,
) : ViewModel() {
    // UI states
    private val _reviewsUiState = MutableStateFlow(EntrepreneurshipReviewsUiState())
    val reviewsUiState: StateFlow<EntrepreneurshipReviewsUiState> = _reviewsUiState.asStateFlow()

    // Action states
    private val _reviewsActionState = MutableStateFlow<EntrepreneurshipReviewsActionState>(EntrepreneurshipReviewsActionState.Idle)
    val reviewsActionState: StateFlow<EntrepreneurshipReviewsActionState> = _reviewsActionState.asStateFlow()

    fun loadReviewDetails(entrepreneurshipId: UUID){
        viewModelScope.launch {
            val reviewRating = getEntrepreneurshipRatingUseCase(entrepreneurshipId)
            _reviewsUiState.value = _reviewsUiState.value.copy(
                averageRating = reviewRating.avgReview,
                totalReviews = reviewRating.totalReviews
            )
        }
    }

    fun loadMoreReviews(entrepreneurshipId: UUID) {
        _reviewsActionState.value = EntrepreneurshipReviewsActionState.Loading

        viewModelScope.launch {
            try {
                val reviews = getAllEntrepreneurshipReviewsUseCase(
                    entrepreneurshipId = entrepreneurshipId,
                    page = _reviewsUiState.value.page + 1,
                    limit = 2
                )

                if (reviews.isEmpty()) {
                    _reviewsUiState.value = _reviewsUiState.value.copy(hasMoreItems = false)
                } else {
                    _reviewsUiState.value = _reviewsUiState.value.copy(
                        reviews = _reviewsUiState.value.reviews.plus(
                            reviews.map { review ->
                                CommentData(
                                    userId = review.id,
                                    userName = review.userCreated.firstName + " " + review.userCreated.lastName,
                                    userImageUrl = review.userCreated.profile.profilePicture,
                                    rating = review.rating,
                                    comment = review.comment,
                                    date = review.dateCreated
                                )
                            }
                        ),
                        page = _reviewsUiState.value.page + 1
                    )
                    Log.i("EntrepreneurshipDetailsViewModel", "Page: ${_reviewsUiState.value.page}")
                    Log.i("EntrepreneurshipDetailsViewModel", "Reviews: ${_reviewsUiState.value.reviews}")
                }
                _reviewsActionState.value = EntrepreneurshipReviewsActionState.Idle
            } catch (e: Exception) {
                _reviewsActionState.value = EntrepreneurshipReviewsActionState.Error("Error al cargar más reseñas: ${e.message}")
            }
        }
    }
}