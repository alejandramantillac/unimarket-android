package com.codeoflegends.unimarket.core.data.model

import com.codeoflegends.unimarket.core.data.model.UserProfile
import java.util.UUID

data class User(
    val id: UUID,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profile: UserProfile = UserProfile()
)