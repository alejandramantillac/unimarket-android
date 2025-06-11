package com.codeoflegends.unimarket.features.product.ui.viewModel.validation

import com.codeoflegends.unimarket.core.validation.FormField
import com.codeoflegends.unimarket.core.validation.FormState
import com.codeoflegends.unimarket.core.validation.validators.AboveZeroValidator
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyListValidator
import com.codeoflegends.unimarket.core.validation.validators.NotEmptyValidator
import com.codeoflegends.unimarket.core.validation.validators.NotNullValidator
import com.codeoflegends.unimarket.features.product.ui.viewModel.state.ProductFormData

/**
 * Centralized validation logic for product forms
 */
object ProductValidation {

    /**
     * Creates a FormState for validating product data
     */
    fun createFormState(formData: ProductFormData, isEdit: Boolean): FormState {
        return FormState(
            fields = buildMap {
                put(
                    "category",
                    FormField(
                        formData.selectedCategory,
                        listOf(NotNullValidator("Selecciona una categoría"))
                    )
                )
                put(
                    "name",
                    FormField(formData.name, listOf(NotEmptyValidator("El nombre es obligatorio")))
                )
                put(
                    "description",
                    FormField(
                        formData.description,
                        listOf(NotEmptyValidator("La descripción es obligatoria"))
                    )
                )
                put(
                    "price",
                    FormField(
                        formData.price,
                        listOf(AboveZeroValidator("El precio debe ser mayor a 0"))
                    )
                )
                put(
                    "lowStockAlert",
                    FormField(
                        formData.lowStockAlert,
                        listOf(AboveZeroValidator("La alerta de stock debe ser mayor a 0"))
                    )
                )
                put(
                    "variants",
                    FormField(
                        formData.variants,
                        listOf(NotEmptyListValidator("Agrega al menos una variante"))
                    )
                )
                put(
                    "specifications",
                    FormField(
                        formData.specifications,
                        listOf(NotEmptyListValidator("Agrega al menos una especificación"))
                    )
                )
            }
        )
    }

    /**
     * Performs full validation of the product form
     */
    fun validateForm(formData: ProductFormData, isEdit: Boolean): Boolean {
        return createFormState(formData, isEdit).validateAll()
    }
}