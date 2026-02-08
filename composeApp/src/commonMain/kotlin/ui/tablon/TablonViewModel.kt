package ui.tablon

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.*
import repositories.CompeticionRepository
import casos.GenerarTablon

class TablonViewModel(
    private val generarTablonUseCase: GenerarTablon = GenerarTablon()
) : ViewModel() {

    val competicion = mutableStateOf<Competicion?>(null)
    val tablon = mutableStateOf<Tablon?>(null)
    val rondas = mutableStateListOf<List<Asalto>>()

    init {
        competicion.value = CompeticionRepository.get()
        val t = competicion.value?.tablon
        if (t != null && t.rondas.isNotEmpty()) {
            tablon.value = t
            rondas.addAll(t.rondas)
        } else {
            generarTablon()
        }
    }

    fun generarTablon() {
        competicion.value?.let { comp ->
            if (comp.clasificacion.isNotEmpty()) {
                val nuevoTablon = generarTablonUseCase.execute(comp.clasificacion)
                tablon.value = nuevoTablon
                rondas.clear()
                rondas.addAll(nuevoTablon.rondas)

                val nuevaComp = comp.copy(tablon = nuevoTablon)
                CompeticionRepository.set(nuevaComp)
                competicion.value = nuevaComp
            }
        }
    }

    fun actualizarAsalto(rondaIndex: Int, asaltoIndex: Int, t1: Int, t2: Int) {
        if (rondaIndex >= rondas.size) return
        
        val asaltosRonda = rondas[rondaIndex].toMutableList()
        if (asaltoIndex >= asaltosRonda.size) return

        asaltosRonda[asaltoIndex] = asaltosRonda[asaltoIndex].copy(
            tocados1 = t1,
            tocados2 = t2
        )
        
        rondas[rondaIndex] = asaltosRonda
        guardarCambios()
    }

    fun avanzarRonda() {
        val ultimaRonda = rondas.lastOrNull() ?: return
        if (ultimaRonda.size < 1) return 
        if (ultimaRonda.size == 1 && (ultimaRonda[0].tocados1 > 0 || ultimaRonda[0].tocados2 > 0)) {
            // Es la final y ya tiene resultado
            return
        }

        val ganadores = ultimaRonda.map { asalto ->
            if (asalto.tocados1 >= asalto.tocados2) asalto.tirador1 else asalto.tirador2
        }

        val nuevaRonda = mutableListOf<Asalto>()
        for (i in ganadores.indices step 2) {
            if (i + 1 < ganadores.size) {
                nuevaRonda.add(Asalto(tirador1 = ganadores[i], tirador2 = ganadores[i+1]))
            } else {
                // Si hay un número impar (bye), el tirador pasa solo o espera
                // Por ahora lo omitimos o podrías añadir un asalto contra "vacío"
            }
        }

        if (nuevaRonda.isNotEmpty()) {
            rondas.add(nuevaRonda)
            guardarCambios()
        }
    }

    private fun guardarCambios() {
        val comp = competicion.value ?: return
        val nuevoTablon = Tablon(rondas = rondas.toList())
        val nuevaComp = comp.copy(tablon = nuevoTablon)
        CompeticionRepository.set(nuevaComp)
        competicion.value = nuevaComp
    }
}
