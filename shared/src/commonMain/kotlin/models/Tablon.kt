package models

import kotlinx.serialization.Serializable

@Serializable
data class Tablon(
    val rondas: List<List<Asalto>>
)
