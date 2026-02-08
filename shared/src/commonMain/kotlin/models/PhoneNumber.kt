package models

import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumber(
    val type: String,
    val number: String
)
