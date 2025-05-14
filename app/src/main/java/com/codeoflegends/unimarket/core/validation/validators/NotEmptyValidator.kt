package com.codeoflegends.unimarket.core.validation.validators

import com.codeoflegends.unimarket.core.validation.Validator

class NotEmptyValidator(
    override val errorMessage: String = "Este campo no puede estar vacío"
) : Validator<String> {
    override fun validate(value: String) = value.isNotBlank()
}
