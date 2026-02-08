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

class ArbitrosViewModel(
    private val repo: ArbitrosRepository = ArbitrosRepository()
): ViewModel() {

    val arbitros = mutableStateListOf<Arbitro>()
    val jsonInput = mutableStateOf("")

    init {
        cargarDesdeRecursos()
    }

    @OptIn(ExperimentalResourceApi::class)
    fun cargarDesdeRecursos() {
        viewModelScope.launch {
            try {
                // Ruta actualizada a la subcarpeta 'files'
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
            repo.loadFromJson(json)
            arbitros.clear()
            arbitros.addAll(repo.getAll())
        } catch (e: Exception) {
            println("Error al decodificar JSON de árbitros: ${e.message}")
        }
    }

    fun eliminar(federateNumber: Int) {
        repo.delete(federateNumber)
        arbitros.removeAll { it.federateNumber == federateNumber }
    }

    fun añadir(arbitro: Arbitro) {
        repo.add(arbitro)
        arbitros.add(arbitro)
    }

    fun actualizar(federateNumber: Int, nuevo: Arbitro) {
        repo.update(federateNumber, nuevo)
        val index = arbitros.indexOfFirst { it.federateNumber == federateNumber }
        if (index != -1) arbitros[index] = nuevo
    }
}
