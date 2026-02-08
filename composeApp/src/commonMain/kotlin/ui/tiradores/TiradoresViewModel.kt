package ui.tiradores

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esgrima.composeapp.generated.resources.Res
import kotlinx.coroutines.launch
import models.Tirador
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repositories.TiradoresRepository

class TiradoresViewModel : ViewModel() {

    val tiradores = mutableStateListOf<Tirador>()
    val jsonInput = mutableStateOf("")

    init {
        cargarDesdeRecursos()
    }

    @OptIn(ExperimentalResourceApi::class)
    fun cargarDesdeRecursos() {
        viewModelScope.launch {
            try {
                val bytes = Res.readBytes("files/tiradores.txt")
                val json = bytes.decodeToString()
                if (json.isNotBlank()) {
                    cargarDesdeJson(json)
                }
            } catch (e: Exception) {
                println("Error cargando tiradores: ${e.message}")
            }
        }
    }

    fun cargarDesdeJson(json: String) {
        try {
            if (json.isBlank()) return
            TiradoresRepository.loadFromJson(json)
            tiradores.clear()
            tiradores.addAll(TiradoresRepository.getAll())
        } catch (e: Exception) {
            println("Error al decodificar JSON de tiradores: ${e.message}")
        }
    }

    fun eliminar(federateNumber: Int) {
        TiradoresRepository.delete(federateNumber)
        tiradores.removeAll { it.federateNumber == federateNumber }
    }

    fun añadir(tirador: Tirador) {
        TiradoresRepository.add(tirador)
        tiradores.add(tirador)
    }

    fun actualizar(federateNumber: Int, nuevo: Tirador) {
        TiradoresRepository.update(federateNumber, nuevo)
        val index = tiradores.indexOfFirst { it.federateNumber == federateNumber }
        if (index != -1) tiradores[index] = nuevo
    }
}
