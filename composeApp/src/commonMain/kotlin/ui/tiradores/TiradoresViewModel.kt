package ui.tiradores

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.Tirador
import repositories.TiradoresRepository

class TiradoresViewModel(
    private val repo: TiradoresRepository = TiradoresRepository()
) : ViewModel() {

    val tiradores = mutableStateListOf<Tirador>()
    val jsonInput = mutableStateOf("")

    init {
        // Cargar vacío al inicio
    }

    fun cargarDesdeJson(json: String) {
        repo.loadFromJson(json)
        tiradores.clear()
        tiradores.addAll(repo.getAll())
    }

    fun eliminar(federateNumber: Int) {
        repo.delete(federateNumber)
        tiradores.removeAll { it.federateNumber == federateNumber }
    }

    fun añadir(tirador: Tirador) {
        repo.add(tirador)
        tiradores.add(tirador)
    }

    fun actualizar(federateNumber: Int, nuevo: Tirador) {
        repo.update(federateNumber, nuevo)
        val index = tiradores.indexOfFirst { it.federateNumber == federateNumber }
        if (index != -1) tiradores[index] = nuevo
    }
}
