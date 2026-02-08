package models

import kotlinx.serialization.Serializable

@Serializable
enum class Modality {
    SABLE,
    ESPADA,
    FLORETE;

    companion object {
        fun fromString(value: String): Modality? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}
