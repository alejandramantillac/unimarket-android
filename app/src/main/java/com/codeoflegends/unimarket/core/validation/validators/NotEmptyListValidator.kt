package com.codeoflegends.unimarket.core.validation.validators

import com.codeoflegends.unimarket.core.validation.Validator

class NotEmptyListValidator<T>(
    override val errorMessage: String = "Este campo no puede estar vacío"
) : Validator<List<T>?> {
    override fun validate(value: List<T>?) =
        value?.isNotEmpty() == true
}
