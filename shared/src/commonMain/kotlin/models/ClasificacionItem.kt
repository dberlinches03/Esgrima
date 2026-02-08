package models

import kotlinx.serialization.Serializable

@Serializable
data class ClasificacionItem(
    val tirador: Tirador,
    val victorias: Int,
    val tocadosDados: Int,
    val tocadosRecibidos: Int,
    val indice: Int
)
