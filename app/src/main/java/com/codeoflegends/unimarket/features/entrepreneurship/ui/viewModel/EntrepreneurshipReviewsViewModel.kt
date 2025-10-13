package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.core.ui.components.CommentData
import com.codeoflegends.unimarket.features.auth.data.repositories.interfaces.AuthRepository
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.ReviewRating
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipRatingUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetAllEntrepreneurshipReviewsUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.CreateEntrepreneurshipReviewUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.GetOwnEntrepreneurshipReviewUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.entrepreneurshipReview.DeleteEntrepreneurshipReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject

sealed class EntrepreneurshipReviewsActionState {
    object Idle : EntrepreneurshipReviewsActionState()
    data class Error(val message: String) : EntrepreneurshipReviewsActionState()
    object Loading : EntrepreneurshipReviewsActionState()
}

sealed class EntrepreneurshipSendReviewActionState {
    object Idle : EntrepreneurshipSendReviewActionState()
    data class Error(val message: String) : EntrepreneurshipSendReviewActionState()
    object Loading : EntrepreneurshipSendReviewActionState()
    object Success : EntrepreneurshipSendReviewActionState()
}

sealed class EntrepreneurshipDeleteReviewActionState {
    object Idle : EntrepreneurshipDeleteReviewActionState()
    data class Error(val message: String) : EntrepreneurshipDeleteReviewActionState()
    object Loading : EntrepreneurshipDeleteReviewActionState()
    object Success : EntrepreneurshipDeleteReviewActionState()
}

data class EntrepreneurshipUserReviewUiState(
    val rating: Int = 0,
    val comment: String = "",
    val review: ReviewRating? = null
)

@HiltViewModel
class EntrepreneurshipReviewsViewModel @Inject constructor(
    private val getAllEntrepreneurshipReviewsUseCase: GetAllEntrepreneurshipReviewsUseCase,
    private val getOwnEntrepreneurshipReviewUseCase: GetOwnEntrepreneurshipReviewUseCase,
    private val getEntrepreneurshipRatingUseCase: GetEntrepreneurshipRatingUseCase,
    private val createEntrepreneurshipReviewUseCase: CreateEntrepreneurshipReviewUseCase,
    private val deleteEntrepreneurshipReviewUseCase: DeleteEntrepreneurshipReviewUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {
    // UI states
    private val _reviewsUiState = MutableStateFlow(EntrepreneurshipReviewsUiState())
    val reviewsUiState: StateFlow<EntrepreneurshipReviewsUiState> = _reviewsUiState.asStateFlow()

    private val _userReviewUiState = MutableStateFlow(EntrepreneurshipUserReviewUiState())
    val userReviewUiState: StateFlow<EntrepreneurshipUserReviewUiState> = _userReviewUiState.asStateFlow()

    // Action states
    private val _reviewsActionState = MutableStateFlow<EntrepreneurshipReviewsActionState>(EntrepreneurshipReviewsActionState.Idle)
    val reviewsActionState: StateFlow<EntrepreneurshipReviewsActionState> = _reviewsActionState.asStateFlow()

    private val _sendReviewActionState = MutableStateFlow<EntrepreneurshipSendReviewActionState>(EntrepreneurshipSendReviewActionState.Idle)
    val sendReviewActionState: StateFlow<EntrepreneurshipSendReviewActionState> = _sendReviewActionState.asStateFlow()

    private val _deleteReviewActionState = MutableStateFlow<EntrepreneurshipDeleteReviewActionState>(EntrepreneurshipDeleteReviewActionState.Idle)
    val deleteReviewActionState: StateFlow<EntrepreneurshipDeleteReviewActionState> = _deleteReviewActionState.asStateFlow()

    fun loadReviewDetails(entrepreneurshipId: UUID){
        viewModelScope.launch {
            try {
                val reviewRating = getEntrepreneurshipRatingUseCase(entrepreneurshipId)
                _reviewsUiState.value = _reviewsUiState.value.copy(
                    averageRating = reviewRating.avgReview,
                    totalReviews = reviewRating.totalReviews
                )

                try {
                    val ownReview = getOwnEntrepreneurshipReviewUseCase(
                        entrepreneurshipId = entrepreneurshipId,
                        authorId = authRepository.getCurrentUserId()!!
                    )
                    if (ownReview != null) {
                        _reviewsUiState.value = _reviewsUiState.value.copy(
                            ownReview = CommentData(
                                userId = ownReview.id,
                                userName = ownReview.userCreated.firstName + " " + ownReview.userCreated.lastName,
                                userImageUrl = ownReview.userCreated.profile.profilePicture.orEmpty(),
                                rating = ownReview.rating,
                                comment = ownReview.comment,
                                date = ownReview.dateCreated
                            )
                        )
                    }
                } catch (e: retrofit2.HttpException) {
                    when (e.code()) {
                        403 -> {
                            Log.w("EntrepreneurshipReviewsViewModel", "User doesn't have permission to view own review: ${e.message()}")
                            // Continue without setting ownReview - this is not a critical error
                        }
                        401 -> {
                            Log.e("EntrepreneurshipReviewsViewModel", "Authentication failed: ${e.message()}")
                            _reviewsActionState.value = EntrepreneurshipReviewsActionState.Error("Tu sesión ha expirado. Por favor, vuelve a iniciar sesión.")
                        }
                        else -> {
                            Log.e("EntrepreneurshipReviewsViewModel", "Error loading own review: ${e.code()} - ${e.message()}")
                            _reviewsActionState.value = EntrepreneurshipReviewsActionState.Error("Error al cargar tu reseña: ${e.message()}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("EntrepreneurshipReviewsViewModel", "Unexpected error loading own review: ${e.message}", e)
                    _reviewsActionState.value = EntrepreneurshipReviewsActionState.Error("Error inesperado al cargar tu reseña: ${e.message}")
                }
            } catch (e: Exception) {
                Log.e("EntrepreneurshipReviewsViewModel", "Error loading review details: ${e.message}", e)
                _reviewsActionState.value = EntrepreneurshipReviewsActionState.Error("Error al cargar los detalles de las reseñas: ${e.message}")
            }
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
                    val currentUserId = authRepository.getCurrentUserId()
                    val filteredReviews = reviews
                        .filter { review -> review.userCreated.id != currentUserId }
                        .map { review ->
                            CommentData(
                                userId = review.id,
                                userName = review.userCreated.firstName + " " + review.userCreated.lastName,
                                userImageUrl = review.userCreated.profile.profilePicture.orEmpty(),
                                rating = review.rating,
                                comment = review.comment,
                                date = review.dateCreated
                            )
                        }

                    _reviewsUiState.value = _reviewsUiState.value.copy(
                        reviews = _reviewsUiState.value.reviews.plus(filteredReviews),
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

    fun sendReview(entrepreneurshipId: UUID, rating: Int, comment: String) {
        _sendReviewActionState.value = EntrepreneurshipSendReviewActionState.Loading

        viewModelScope.launch {
            Log.i("EntrepreneurshipReviewsViewModel", "Sending review: entrepreneurshipId=$entrepreneurshipId, rating=$rating, comment=$comment")
            try {
                createEntrepreneurshipReviewUseCase(
                    entrepreneurship = entrepreneurshipId,
                    rating = rating,
                    comment = comment
                )
                Log.i("EntrepreneurshipReviewsViewModel", "Review sent successfully")
                
                // Recargar los detalles después de crear la reseña exitosamente
                loadReviewDetails(entrepreneurshipId)
                
                // Limpiar la lista de reviews para que se recargue sin el ownReview
                _reviewsUiState.value = _reviewsUiState.value.copy(
                    reviews = emptyList(),
                    page = 0,
                    hasMoreItems = true
                )
                
                _sendReviewActionState.value = EntrepreneurshipSendReviewActionState.Success
                
                // Resetear el estado después de un breve delay
                kotlinx.coroutines.delay(1000)
                _sendReviewActionState.value = EntrepreneurshipSendReviewActionState.Idle
                
            } catch (e: Exception) {
                Log.e("EntrepreneurshipReviewsViewModel", "Error sending review: ${e.message}", e)
                _sendReviewActionState.value = EntrepreneurshipSendReviewActionState.Error("Error al enviar la reseña: ${e.message}")
            }
        }
    }

    fun resetSendReviewState() {
        _sendReviewActionState.value = EntrepreneurshipSendReviewActionState.Idle
    }

    fun deleteOwnReview(entrepreneurshipId: UUID) {
        _deleteReviewActionState.value = EntrepreneurshipDeleteReviewActionState.Loading
        
        viewModelScope.launch {
            try {
                val ownReview = _reviewsUiState.value.ownReview
                if (ownReview != null) {
                    deleteEntrepreneurshipReviewUseCase(ownReview.userId.toString())
                    
                    // Limpiar el ownReview del estado
                    _reviewsUiState.value = _reviewsUiState.value.copy(
                        ownReview = null
                    )
                    
                    // Recargar los detalles para actualizar el rating
                    loadReviewDetails(entrepreneurshipId)
                    
                    _deleteReviewActionState.value = EntrepreneurshipDeleteReviewActionState.Success
                } else {
                    _deleteReviewActionState.value = EntrepreneurshipDeleteReviewActionState.Error("No hay reseña para eliminar")
                }
            } catch (e: Exception) {
                _deleteReviewActionState.value = EntrepreneurshipDeleteReviewActionState.Error("Error al eliminar la reseña: ${e.message}")
            }
        }
    }
}

