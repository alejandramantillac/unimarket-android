package com.codeoflegends.unimarket.core.validation

data class FormField<T>(
    var value: T,
    val validators: List<Validator<T>>,
    var error: String? = null
) {
    fun validate(): Boolean {
        for (validator in validators) {
            if (!validator.validate(value)) {
                error = validator.errorMessage
                return false
            }
        }
        error = null
        return true
    }
}
