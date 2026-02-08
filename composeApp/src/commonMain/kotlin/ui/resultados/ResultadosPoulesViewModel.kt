package ui.resultados

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.*
import repositories.CompeticionRepository

class ResultadosPoulesViewModel : ViewModel() {

    val competicion = mutableStateOf<Competicion?>(null)
    val poules = mutableStateListOf<Poule>()

    init {
        // Usar el Singleton directamente
        competicion.value = CompeticionRepository.get()
        competicion.value?.poules?.let { poules.addAll(it) }
    }

    fun actualizarAsalto(
        pouleIndex: Int,
        asaltoIndex: Int,
        tocados1: Int,
        tocados2: Int
    ) {
        val poule = poules[pouleIndex]
        val asaltos = poule.asaltos.toMutableList()

        val actualizado = asaltos[asaltoIndex].copy(
            tocados1 = tocados1,
            tocados2 = tocados2
        )

        asaltos[asaltoIndex] = actualizado

        poules[pouleIndex] = poule.copy(asaltos = asaltos)

        guardarCambios()
    }

    private fun guardarCambios() {
        val comp = competicion.value ?: return
        val nuevaComp = comp.copy(poules = poules.toList())
        CompeticionRepository.set(nuevaComp)
        competicion.value = nuevaComp
    }
}
