package models

import kotlinx.serialization.Serializable

@Serializable
data class Poule(
    val numero: Int,
    val asaltos: List<Asalto>,
    val arbitro: Arbitro? = null,
    val pista: Int = 0
)
