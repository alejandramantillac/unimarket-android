package com.codeoflegends.unimarket.features.product.ui.viewModel.state

/**
 * Represents the action state for product-related operations
 */
sealed class ProductActionState {
    /**
     * Initial state or when an action has been reset
     */
    object Idle : ProductActionState()
    
    /**
     * Action completed successfully
     */
    object Success : ProductActionState()
    
    /**
     * Action failed with an error message
     */
    data class Error(val message: String) : ProductActionState()
    
    /**
     * Action is in progress
     */
    object Loading : ProductActionState()
} 