package com.codeoflegends.unimarket.features.entrepreneurship.ui.viewModel


import android.net.Uri
import android.util.Log
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.Entrepreneurship
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.EntrepreneurshipCustomization
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SocialNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.CreateEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.model.SubscriptionPlan
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.DeleteEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetAllEntrepreneurship
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.GetEntrepreneurshipUseCase
import com.codeoflegends.unimarket.features.entrepreneurship.data.usecase.UpdateEntrepreneurshipUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

sealed class EntrepreneurshipActionState {
    object Idle : EntrepreneurshipActionState()
    object Success : EntrepreneurshipActionState()
    data class Error(val message: String) : EntrepreneurshipActionState()
    object Loading : EntrepreneurshipActionState()
}

@HiltViewModel
class EntrepreneurshipViewModel @Inject constructor(
    private val createEntrepreneurshipUseCase: CreateEntrepreneurshipUseCase,
    private val getAllEntrepreneurshipUseCase: GetAllEntrepreneurship,
    private val getEntrepreneurshipUseCase: GetEntrepreneurshipUseCase,
    private val deleteEntrepreneurshipUseCase: DeleteEntrepreneurshipUseCase,
    private val updateEntrepreneurshipUseCase: UpdateEntrepreneurshipUseCase
) : ViewModel() {

    private val defaultCategoriesOptions = listOf(
        "Comida",
        "Electrónica",
        "Joyeria",
        "Decoración",
    )


    private val currentYear = LocalDate.now().year
    val yearOptions: List<String> = (1950..currentYear).map { it.toString() }.reversed()

    private val defaultStatusOptions = listOf("Activo", "Inactivo")

    private val defaultSubscriptionPlans = listOf(
        SubscriptionPlan(UUID.fromString("00000000-0000-0000-0000-000000000001"), "Básico", 0.0, "Plan gratuito", "Soporte limitado"),
        SubscriptionPlan(UUID.fromString("00000000-0000-0000-0000-000000000002"), "Pro", 9.99, "Plan profesional", "Estadísticas y soporte"),
        SubscriptionPlan(UUID.fromString("00000000-0000-0000-0000-000000000003"), "Premium", 19.99, "Plan completo", "Todo incluido")
    )


    private val _uiState = MutableStateFlow(
        EntrepreneurshipUiState(
            categoryOptions = defaultCategoriesOptions,
            yearOptions = yearOptions,
            entrepreneurshipCategory = "0",
            selectedCategory = defaultCategoriesOptions[0],
            entrepreneurshipSocialNetworks = listOf(
                SocialNetwork(id = 1, platform = "Instagram", url = ""),
                SocialNetwork(id = 2, platform = "TikTok", url = "")
            ) ,
            entrepreneurshipStatus = defaultStatusOptions[0],
            entrepreneurshipSubscription = defaultSubscriptionPlans[0].id.toString(),
            statusOptions = defaultStatusOptions,
            subscriptionOptions= defaultSubscriptionPlans
        )
    )

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipName = name)
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.value = _uiState.value.copy(entrepreneurshipImageUri = uri)
    }


    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipDescription = description)
    }


    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipEmail = email)
    }

    fun onPhoneChanged(phone: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipPhone = phone)
    }

    fun onSloganChanged(slogan: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipSlogan = slogan)
    }

    fun onSubscriptionChanged(subscriptionId: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipSubscription = subscriptionId)
    }


    fun onStatusChanged(status: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipStatus = status)
    }

    fun onSocialNetworkChanged(index: Int, url: String) {
        val updatedList = _uiState.value.entrepreneurshipSocialNetworks.toMutableList()
        val updatedItem = updatedList[index].copy(url = url)
        updatedList[index] = updatedItem
        _uiState.value = _uiState.value.copy(entrepreneurshipSocialNetworks = updatedList)
    }

    fun onSocialNetworkPlatformChanged(index: Int, platform: String) {
        val updatedList = _uiState.value.entrepreneurshipSocialNetworks.toMutableList()
        val updatedItem = updatedList[index].copy(platform = platform)
        updatedList[index] = updatedItem
        _uiState.value = _uiState.value.copy(entrepreneurshipSocialNetworks = updatedList)
    }

    fun addSocialNetwork() {
        val newId = (_uiState.value.entrepreneurshipSocialNetworks.maxOfOrNull { it.id } ?: 0) + 1
        val updatedList = _uiState.value.entrepreneurshipSocialNetworks + SocialNetwork(id = newId, platform = "", url = "")
        _uiState.value = _uiState.value.copy(entrepreneurshipSocialNetworks = updatedList)
    }

    fun removeSocialNetwork(index: Int) {
        val updatedList = _uiState.value.entrepreneurshipSocialNetworks.toMutableList().apply {
            removeAt(index)
        }
        _uiState.value = _uiState.value.copy(entrepreneurshipSocialNetworks = updatedList)
    }


    fun onUserFounderChanged(userFounder: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipUserFounder = userFounder)
    }

    fun onCustomizationChanged(customization: String) {
        _uiState.value = _uiState.value.copy(entrepreneurshipCustomization = customization)
    }

    fun onYearSelected(year: String) {
        _uiState.value = _uiState.value.copy(
            selectedYear = year
        )
    }


    fun onTabSelected(tab: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }
    val uiState: StateFlow<EntrepreneurshipUiState> = _uiState.asStateFlow()

    private val _actionState = MutableStateFlow<EntrepreneurshipActionState>(EntrepreneurshipActionState.Idle)
    val actionState: StateFlow<EntrepreneurshipActionState> = _actionState.asStateFlow()

    fun onCategorySelected(categoryName: String) {
        val index = defaultCategoriesOptions.indexOf(categoryName).takeIf { it >= 0 } ?: 0
        _uiState.value = _uiState.value.copy(
            selectedCategory = categoryName,
            entrepreneurshipCategory = index.toString()
        )
    }

    fun saveEntrepreneurship() {
        val state = _uiState.value

        if (state.entrepreneurshipName.isBlank() || state.entrepreneurshipCategory.isBlank()) {
            _actionState.value = EntrepreneurshipActionState.Error("Completa todos los campos obligatorios.")
            return
        }

        try {
            val entrepreneurship = Entrepreneurship(
                id = state.id,
                name = state.entrepreneurshipName,
                slogan = state.entrepreneurshipSlogan,
                description = state.entrepreneurshipDescription,
                creationDate = LocalDateTime.of(
                    state.entrepreneurshipYear.toIntOrNull() ?: 2000,
                    1,
                    1,
                    0,
                    0
                ),
                email = state.entrepreneurshipEmail,
                phone = state.entrepreneurshipPhone,
                subscription = UUID.fromString(state.entrepreneurshipSubscription),
                status = state.entrepreneurshipStatus,
                category = state.entrepreneurshipCategory.toIntOrNull() ?: 0,
                socialNetworks = state.entrepreneurshipSocialNetworks,
                userFounder = UUID.fromString(state.entrepreneurshipUserFounder),
                customization = EntrepreneurshipCustomization(
                    profileImg = state.profileImg,
                    bannerImg = state.bannerImg,
                    color1 = state.color1,
                    color2 = state.color2
                ),
            )

            viewModelScope.launch {
                _actionState.value = EntrepreneurshipActionState.Loading
                try {
                    createEntrepreneurshipUseCase(entrepreneurship)
                    _actionState.value = EntrepreneurshipActionState.Success
                } catch (e: Exception) {
                    _actionState.value = EntrepreneurshipActionState.Error("Error: ${e.message}")
                }
            }

        } catch (e: Exception) {
            _actionState.value = EntrepreneurshipActionState.Error("Error en los datos: ${e.message}")
        }
    }

    fun clearForm() {
        _uiState.value = EntrepreneurshipUiState(
            categoryOptions = defaultCategoriesOptions,
            entrepreneurshipCategory = "0",
            selectedCategory = defaultCategoriesOptions[0]
        )
    }

    fun loadEntrepreneurship(entrepreneurshipId: String?) {
        if (entrepreneurshipId.isNullOrEmpty()) {
            _uiState.value = _uiState.value.copy(entrepreneurship = null)
            return
        }

        val uuid = try {
            UUID.fromString(entrepreneurshipId)
        } catch (e: Exception) {
            null
        }

        Log.i("Debug", "El id del empredimiento es: $uuid")

        if (uuid == null) {
            _actionState.value = EntrepreneurshipActionState.Error("ID del emprendimiento inválido")
            return
        }

        _actionState.value = EntrepreneurshipActionState.Loading

        viewModelScope.launch {
            try {
                val entrepreneurship = getEntrepreneurshipUseCase(uuid)
                Log.i("Debug", "Información del emprendimiento: $entrepreneurship")
                _uiState.value = _uiState.value.copy(
                    id = entrepreneurship.id,
                    name = entrepreneurship.name,
                    slogan = entrepreneurship.slogan,
                    description = entrepreneurship.description,
                    email = entrepreneurship.email,
                    phone = entrepreneurship.phone,
                    profileImg =  entrepreneurship.customization.profileImg,
                    bannerImg = entrepreneurship.customization.bannerImg,
                    color1 = entrepreneurship.customization.color1,
                    color2 = entrepreneurship.customization.color2,
                    tags = entrepreneurship.tags,
                    entrepreneurship = entrepreneurship
                )
                Log.i("Debug", "Información del emprendimiento: $entrepreneurship")
                _actionState.value = EntrepreneurshipActionState.Idle
            } catch (e: Exception) {
                _actionState.value = EntrepreneurshipActionState.Error("Error al cargar el emprendimiento: ${e.message}")
                Log.i("Debug", "Error al cargar el emprendimiento: ${e.message}")
            }
        }
    }


    fun onNavigationItemSelected(route: String) {
        _uiState.value = _uiState.value.copy(currentRoute = route)
    }
}