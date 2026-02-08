package ui.clasificacion

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.ClasificacionItem
import models.Competicion
import repositories.CompeticionRepository
import casos.CalcularClasificacion

class ClasificacionViewModel(
    private val compRepo: CompeticionRepository = CompeticionRepository(),
    private val calcularUseCase: CalcularClasificacion = CalcularClasificacion()
) : ViewModel() {

    val competicion = mutableStateOf<Competicion?>(null)
    val clasificacion = mutableStateListOf<ClasificacionItem>()

    init {
        competicion.value = compRepo.get()
        competicion.value?.let { comp ->
            val lista = calcularUseCase.execute(comp.poules)
            clasificacion.addAll(lista)

            // Guardar en la competición
            val nuevaComp = comp.copy(clasificacion = lista)
            compRepo.set(nuevaComp)
            competicion.value = nuevaComp
        }
    }
}
