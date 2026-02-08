package ui.tablon

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.*
import repositories.CompeticionRepository
import casos.GenerarTablon

class TablonViewModel(
    private val compRepo: CompeticionRepository = CompeticionRepository(),
    private val generarTablonUseCase: GenerarTablon = GenerarTablon()
) : ViewModel() {

    val competicion = mutableStateOf<Competicion?>(null)
    val rondas = mutableStateListOf<List<Asalto>>()

    init {
        val comp = compRepo.get()
        competicion.value = comp

        if (comp != null) {
            if (comp.tablon == null) {
                val nuevoTablon = generarTablonUseCase.execute(comp.clasificacion)
                rondas.addAll(nuevoTablon.rondas)

                val nuevaComp = comp.copy(tablon = nuevoTablon)
                compRepo.set(nuevaComp)
                competicion.value = nuevaComp
            } else {
                rondas.addAll(comp.tablon!!.rondas)
            }
        }
    }

    fun actualizarAsalto(
        rondaIndex: Int,
        asaltoIndex: Int,
        tocados1: Int,
        tocados2: Int
    ) {
        val ronda = rondas[rondaIndex].toMutableList()
        val asalto = ronda[asaltoIndex]

        ronda[asaltoIndex] = asalto.copy(
            tocados1 = tocados1,
            tocados2 = tocados2
        )

        rondas[rondaIndex] = ronda

        guardarCambios()
    }

    fun avanzarRonda() {
        val ultimaRonda = rondas.last()

        val ganadores = ultimaRonda.map { asalto ->
            if (asalto.tocados1 > asalto.tocados2) asalto.tirador1 else asalto.tirador2
        }

        if (ganadores.size <= 1) return

        val nuevaRonda = mutableListOf<Asalto>()

        var left = 0
        var right = ganadores.size - 1

        while (left < right) {
            nuevaRonda.add(
                Asalto(
                    tirador1 = ganadores[left],
                    tirador2 = ganadores[right]
                )
            )
            left++
            right--
        }

        rondas.add(nuevaRonda)
        guardarCambios()
    }

    private fun guardarCambios() {
        val comp = competicion.value ?: return
        val nuevoTablon = Tablon(rondas = rondas.toList())
        val nuevaComp = comp.copy(tablon = nuevoTablon)
        compRepo.set(nuevaComp)
        competicion.value = nuevaComp
    }
}
