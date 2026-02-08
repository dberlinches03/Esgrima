package models

import kotlinx.serialization.Serializable

@Serializable
data class Asalto(
    val arbitro: Arbitro? = null,
    val tirador1: Tirador,
    val tirador2: Tirador,
    var tocados1: Int = 0,
    var tocados2: Int = 0
)
