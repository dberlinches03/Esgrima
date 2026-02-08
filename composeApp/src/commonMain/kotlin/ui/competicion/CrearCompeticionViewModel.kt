package ui.competicion

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.*
import repositories.ArbitrosRepository
import repositories.CompeticionRepository
import repositories.TiradoresRepository

class CrearCompeticionViewModel(
    private val tirRepo: TiradoresRepository = TiradoresRepository(),
    private val arbRepo: ArbitrosRepository = ArbitrosRepository(),
    private val compRepo: CompeticionRepository = CompeticionRepository()
): ViewModel() {

    val nombre = mutableStateOf("")
    val entidadOrganizadora = mutableStateOf("")
    val fecha = mutableStateOf("")
    val lugar = mutableStateOf("")
    val arma = mutableStateOf("espada")

    val tiradores = mutableStateListOf<Tirador>()
    val arbitros = mutableStateListOf<Arbitro>()

    val tiradoresSeleccionados = mutableStateListOf<Tirador>()
    val arbitrosSeleccionados = mutableStateListOf<Arbitro>()

    init {
        // Cargar datos iniciales si ya existen
        tiradores.addAll(tirRepo.getAll())
        arbitros.addAll(arbRepo.getAll())
    }

    fun toggleTirador(t: Tirador) {
        if (tiradoresSeleccionados.contains(t)) tiradoresSeleccionados.remove(t)
        else tiradoresSeleccionados.add(t)
    }

    fun toggleArbitro(a: Arbitro) {
        if (arbitrosSeleccionados.contains(a)) arbitrosSeleccionados.remove(a)
        else arbitrosSeleccionados.add(a)
    }

    fun crearCompeticion(): Competicion {
        val comp = Competicion(
            nombre = nombre.value,
            entidadOrganizadora = entidadOrganizadora.value,
            fecha = fecha.value,
            lugar = lugar.value,
            arma = arma.value,
            tiradores = tiradoresSeleccionados.toList(),
            arbitros = arbitrosSeleccionados.toList()
        )

        compRepo.set(comp)
        return comp
    }
}
