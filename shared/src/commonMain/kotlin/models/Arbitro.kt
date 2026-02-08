package models

import kotlinx.serialization.Serializable

@Serializable
data class Arbitro(
    val firstName: String,
    val lastName: String,
    val federateNumber: Int,
    val club: String,
    val modality: List<String>, // Volvemos a String para evitar romper tus JSONs actuales
    val gender: String = "No especificado",
    val age: Int = 0,
    val country: String = "España",
    val address: Address = Address("", "", "", ""),
    val phoneNumbers: List<PhoneNumber> = emptyList()
)
