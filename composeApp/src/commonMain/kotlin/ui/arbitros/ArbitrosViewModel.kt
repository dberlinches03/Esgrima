package ui.arbitros

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esgrima.composeapp.generated.resources.Res
import kotlinx.coroutines.launch
import models.Arbitro
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repositories.ArbitrosRepository

class ArbitrosViewModel : ViewModel() {

    val arbitros = mutableStateListOf<Arbitro>()
    val jsonInput = mutableStateOf("")

    init {
        cargarDesdeRecursos()
    }

    @OptIn(ExperimentalResourceApi::class)
    fun cargarDesdeRecursos() {
        viewModelScope.launch {
            try {
                val bytes = Res.readBytes("files/arbitros.txt")
                val json = bytes.decodeToString()
                if (json.isNotBlank()) {
                    cargarDesdeJson(json)
                }
            } catch (e: Exception) {
                println("Error cargando árbitros: ${e.message}")
            }
        }
    }

    fun cargarDesdeJson(json: String) {
        try {
            if (json.isBlank()) return
            // Usamos ArbitrosRepository directamente (sin paréntesis)
            ArbitrosRepository.loadFromJson(json)
            arbitros.clear()
            arbitros.addAll(ArbitrosRepository.getAll())
        } catch (e: Exception) {
            println("Error al decodificar JSON de árbitros: ${e.message}")
        }
    }

    fun eliminar(federateNumber: Int) {
        ArbitrosRepository.delete(federateNumber)
        arbitros.removeAll { it.federateNumber == federateNumber }
    }

    fun añadir(arbitro: Arbitro) {
        ArbitrosRepository.add(arbitro)
        arbitros.add(arbitro)
    }

    fun actualizar(federateNumber: Int, nuevo: Arbitro) {
        ArbitrosRepository.update(federateNumber, nuevo)
        val index = arbitros.indexOfFirst { it.federateNumber == federateNumber }
        if (index != -1) arbitros[index] = nuevo
    }
}
