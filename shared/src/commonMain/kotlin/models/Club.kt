package models

import kotlinx.serialization.Serializable

@Serializable
data class Club(
    val nombre: String,
    val federacion: String
)
