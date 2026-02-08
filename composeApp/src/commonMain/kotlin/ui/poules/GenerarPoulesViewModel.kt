package ui.poules

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.*
import repositories.CompeticionRepository
import casos.GenerarPoules

class GenerarPoulesViewModel(
    private val compRepo: CompeticionRepository = CompeticionRepository(),
    private val generarPoulesUseCase: GenerarPoules = GenerarPoules()
): ViewModel() {

    val competicion = mutableStateOf<Competicion?>(null)
    val numeroPoules = mutableStateOf(1)
    val poulesGenerados = mutableStateListOf<Poule>()

    init {
        competicion.value = compRepo.get()
        competicion.value?.let {
            numeroPoules.value = calcularNumeroPoulesSugerido(it.tiradores.size)
        }
    }

    private fun calcularNumeroPoulesSugerido(numTiradores: Int): Int {
        return when {
            numTiradores <= 6 -> 1
            numTiradores <= 12 -> 2
            numTiradores <= 18 -> 3
            numTiradores <= 24 -> 4
            else -> numTiradores / 6
        }
    }

    fun generarPoules() {
        val comp = competicion.value ?: return

        val poules = generarPoulesUseCase.execute(
            tiradores = comp.tiradores,
            numeroPoules = numeroPoules.value,
            arbitros = comp.arbitros
        )

        poulesGenerados.clear()
        poulesGenerados.addAll(poules)

        // Guardar en la competición
        val nuevaComp = comp.copy(poules = poules)
        compRepo.set(nuevaComp)
        competicion.value = nuevaComp
    }
}
