package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Tag
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetAllEntrepreneurshipReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipDetailsActionState {
    object Idle : EntrepreneurshipDetailsActionState()
    object Success : EntrepreneurshipDetailsActionState()
    data class Error(val message: String) : EntrepreneurshipDetailsActionState()
    object Loading : EntrepreneurshipDetailsActionState()
}

@HiltViewModel
class EntrepreneurshipDetailsViewModel @Inject constructor(
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase,
    private val getAllEntrepreneurshipReviewsUseCase: GetAllEntrepreneurshipReviewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntrepreneurshipDetailsUiState())
    val uiState: StateFlow<EntrepreneurshipDetailsUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipDetailsActionState>(EntrepreneurshipDetailsActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipDetailsActionState> = _actionState.asStateFlow()

    fun loadEntrepreneurshipDetails(entrepreneurshipId: UUID) {
        _actionState.value = EntrepreneurshipDetailsActionState.Loading

        viewModelScope.launch {
            try {
                val entrepreneurship = getEntrepreneurshipUseCase(entrepreneurshipId)
                _uiState.value = _uiState.value.copy(
                    slogan = entrepreneurship.slogan,
                    description = entrepreneurship.description,
                    tags = entrepreneurship.tags
                )
                _actionState.value = EntrepreneurshipDetailsActionState.Idle
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipDetailsActionState.Error("Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }

    fun loadMoreReviews(entrepreneurshipId: UUID) {
        _actionState.value = EntrepreneurshipDetailsActionState.Loading

        viewModelScope.launch {
            try {
                val reviews = getAllEntrepreneurshipReviewsUseCase(
                    entrepreneurshipId = entrepreneurshipId,
                    page = _uiState.value.page + 1,
                    limit = 2
                )

                if (reviews.isEmpty()) {
                    _uiState.value = _uiState.value.copy(hasMoreItems = false)
                } else {
                    _uiState.value = _uiState.value.copy(
                        reviews = _uiState.value.reviews.plus(
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
                        page = _uiState.value.page + 1
                    )
                    Log.i("EntrepreneurshipDetailsViewModel", "Page: ${_uiState.value.page}")
                    Log.i("EntrepreneurshipDetailsViewModel", "Reviews: ${_uiState.value.reviews}")
                }

                _actionState.value = EntrepreneurshipDetailsActionState.Idle
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipDetailsActionState.Error("Error al cargar más reseñas: ${e.message}")
            }
        }
    }
}