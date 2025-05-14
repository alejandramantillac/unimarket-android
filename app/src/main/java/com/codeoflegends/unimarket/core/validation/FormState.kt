package com.codeoflegends.unimarket.core.validation

class FormState(
    val fields: Map<String, FormField<*>>
) {
    fun validateAll(): Boolean {
        var isValid = true
        fields.values.forEach { field ->
            if (!field.validate()) isValid = false
        }
        return isValid
    }
}
