package ui.competicion

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esgrima.composeapp.generated.resources.Res
import kotlinx.coroutines.launch
import models.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repositories.ArbitrosRepository
import repositories.CompeticionRepository
import repositories.TiradoresRepository

class CrearCompeticionViewModel : ViewModel() {

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
        cargarDatos()
    }

    private fun cargarDatos() {
        // Primero sincronizamos con lo que ya tengan los repositorios (Singletons)
        actualizarListasDesdeRepos()

        // Si después de sincronizar siguen vacíos, cargamos de archivos
        if (tiradores.isEmpty() || arbitros.isEmpty()) {
            forzarCargaDesdeRecursos()
        }
    }

    private fun actualizarListasDesdeRepos() {
        tiradores.clear()
        tiradores.addAll(TiradoresRepository.getAll())
        arbitros.clear()
        arbitros.addAll(ArbitrosRepository.getAll())
    }

    @OptIn(ExperimentalResourceApi::class)
    private fun forzarCargaDesdeRecursos() {
        viewModelScope.launch {
            try {
                // Cargar Tiradores de archivo si el repo está vacío
                if (TiradoresRepository.getAll().isEmpty()) {
                    val tBytes = Res.readBytes("files/tiradores.txt")
                    TiradoresRepository.loadFromJson(tBytes.decodeToString())
                }
                
                // Cargar Árbitros de archivo si el repo está vacío
                if (ArbitrosRepository.getAll().isEmpty()) {
                    val aBytes = Res.readBytes("files/arbitros.txt")
                    ArbitrosRepository.loadFromJson(aBytes.decodeToString())
                }

                // Sincronizar UI final
                actualizarListasDesdeRepos()
            } catch (e: Exception) {
                println("Error cargando recursos en CrearCompeticionViewModel: ${e.message}")
            }
        }
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

        CompeticionRepository.set(comp)
        return comp
    }
}
