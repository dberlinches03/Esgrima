package ui.arbitros

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.Arbitro
import repositories.ArbitrosRepository

class ArbitrosViewModel(
    private val repo: ArbitrosRepository = ArbitrosRepository()
): ViewModel() {

    val arbitros = mutableStateListOf<Arbitro>()
    val jsonInput = mutableStateOf("")

    fun cargarDesdeJson(json: String) {
        repo.loadFromJson(json)
        arbitros.clear()
        arbitros.addAll(repo.getAll())
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
