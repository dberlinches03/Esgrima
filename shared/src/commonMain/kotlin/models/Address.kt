package models

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val streetAddress: String,
    val city: String,
    val state: String,
    val postalCode: String
)