package ui.poules

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.Competicion
import models.Poule
import repositories.CompeticionRepository
import casos.GenerarPoules

class GenerarPoulesViewModel(
    private val generarPoulesUseCase: GenerarPoules = GenerarPoules()
) : ViewModel() {

    val competicion = mutableStateOf<Competicion?>(null)
    val numeroPoules = mutableStateOf(1)
    val poulesGenerados = mutableStateListOf<Poule>()

    init {
        competicion.value = CompeticionRepository.get()
    }

    fun generarPoules() {
        competicion.value?.let { comp ->
            val poules = generarPoulesUseCase.execute(
                tiradores = comp.tiradores,
                numeroPoules = numeroPoules.value,
                arbitros = comp.arbitros
            )
            poulesGenerados.clear()
            poulesGenerados.addAll(poules)

            // Guardar en la competición
            val nuevaComp = comp.copy(poules = poules)
            CompeticionRepository.set(nuevaComp)
            competicion.value = nuevaComp
        }
    }
}
