package com.codeoflegends.unimarket.core.validation.validators

import com.codeoflegends.unimarket.core.validation.Validator

class NotNullValidator<T> (
    override val errorMessage: String = "Este campo no puede estar vacío"
) : Validator<T> {
    override fun validate(value: T) = value != null
}
