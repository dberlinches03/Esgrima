package domain.model

// Enum para las armas, facilita la consistencia en todo el código.
enum class Arma {
    ESPADA,
    FLORETE,
    SABLE
}

// Representa a un competidor
data class Tirador(
    val id: String, // ID único
    val nombre: String,
    val club: String,
    val licencia: String, // Número de afiliado a la federación
    val ranking: Int = 0 // Ranking inicial, útil para las poules
)

// Representa a un árbitro
data class Arbitro(
    val id: String,
    val nombre: String,
    val licencia: String,
    val especialidades: List<Arma>
)

// Representa un enfrentamiento individual
data class Asalto(
    val id: String,
    val tirador1: Tirador,
    val tirador2: Tirador,
    var tocados1a2: Int = 0, // Tocados que el tirador 1 le da al 2
    var tocados2a1: Int = 0,
    var arbitro: Arbitro? = null,
    var pista: Int? = null,
    var terminado: Boolean = false
)

// Representa un grupo (poule) de la primera fase
data class Poule(
    val id: String,
    val tiradores: List<Tirador>,
    val asaltos: List<Asalto> = emptyList() // Se generarán a partir de los tiradores
)

// Representa la clasificación de un tirador después de las poules
data class ClasificacionItem(
    val tirador: Tirador,
    val victorias: Int,
    val derrotas: Int,
    val tocadosDados: Int,
    val tocadosRecibidos: Int,
    val indice: Int // TD - TR
) {
    val v_m_Ratio: Float = if ((victorias + derrotas) > 0) victorias.toFloat() / (victorias + derrotas).toFloat() else 0f
}

// El objeto principal que contiene todo el estado de la competición
data class Competicion(
    val id: String,
    val nombre: String,
    val fecha: String,
    val lugar: String,
    val arma: Arma,
    val tiradoresInscritos: MutableList<Tirador> = mutableListOf(),
    val arbitrosAsignados: MutableList<Arbitro> = mutableListOf(),
    val poules: MutableList<Poule> = mutableListOf(),
    val clasificacionGeneral: MutableList<ClasificacionItem> = mutableListOf(),
    val tablon: Any? = null
)
