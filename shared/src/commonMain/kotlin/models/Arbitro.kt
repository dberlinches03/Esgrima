package models

import kotlinx.serialization.Serializable

@Serializable
data class Arbitro(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val age: Int,
    val federateNumber: Int,
    val club: String,
    val country: String,
    val modality: List<String>,
    val address: Address,
    val phoneNumbers: List<PhoneNumber>
)
