package models

import kotlinx.serialization.Serializable

@Serializable
data class Competicion(
    val nombre: String,
    val entidadOrganizadora: String,
    val fecha: String,
    val lugar: String,
    val arma: String,
    val tiradores: List<Tirador>,
    val arbitros: List<Arbitro>,
    val poules: List<Poule> = emptyList(),
    val clasificacion: List<ClasificacionItem> = emptyList(),
    val tablon: Tablon? = null
)
