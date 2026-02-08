package models

import kotlinx.serialization.Serializable

@Serializable
data class ClasificacionItem(
    val tirador: Tirador,
    var victorias: Int,
    var tocadosDados: Int,
    var tocadosRecibidos: Int,
    var indice: Int
)
