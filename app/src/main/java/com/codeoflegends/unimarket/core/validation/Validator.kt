package com.codeoflegends.unimarket.core.validation

interface Validator<T> {
    fun validate(value: T): Boolean
    val errorMessage: String
} 